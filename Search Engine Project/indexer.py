import json
from bs4 import BeautifulSoup
from lxml import html
import re
import math
#import nltk
#nltk.download('stopwords')
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize

import os
from collections import defaultdict

import retriever



# initializes integer to keep track of number of docs in corpus
doc_num = 0

# initializes inverted index as a dictionary
inverted = dict()

# initializes a dictionary to keep track of each document and its list of tokens
doc_tokens = defaultdict(list)

#keys lead to URLs containing junk data
keys_to_avoid = ['39/373', '35/269']


file = '/Users/drewcch/PycharmProjects/Assignment3/WEBPAGES_RAW'
database = '/Users/drewcch/PycharmProjects/Assignment3/inverted_index.json'
doc_database = '/Users/drewcch/PycharmProjects/Assignment3/doc_tokens.json'



def print_urls(url_dict):
    for query, urls in url_dict.items():

        if len(urls) == 0:
            print('\nNo URLs found for the query "' + query + '"')
            continue

        print('\nRanked URLs for the query "' + query + '":')
        for url in urls:
            print(url)



def write_to_file(url_dict):
    '''
    Writes out information about the inverted index onto a file. Also writes to the files the URLs that
    were retrieved for the search queries.
    :param url_dict: dictionary that maps each search query to 10 URLs of sites that contain that query
    '''
    with open('report.txt', 'w') as file:
        file.write('Number of Documents in Corpus: ' + str(doc_num) + '\n')
        file.write('Number of Unique Tokens in Index: ' + str(len(inverted)) + '\n')
        file.write('Index takes up ' + str(os.path.getsize(database)/float(1000)) + ' KB\n')
        for query, urls in url_dict.items():
            file.write('\nRanked URLs for the query "' + query + '":\n')
            for url in urls:
                file.write(url + '\n')
    file.close()



def update_idf():
    '''
    Updates the tf-idf score in the inverted index for every corpus within a dictionary term that was originally
    a placeholder value.
    :return:
    '''
    for word, doc_dict in inverted.items():
        idf = math.log10(float(doc_num)/len(inverted[word]))
        for doc_id in doc_dict:
            doc_dict[doc_id]['idf'] = round(idf, 3)
            doc_dict[doc_id]['tf-idf'] *= round((doc_dict[doc_id]['tf-idf'] * idf), 3)




def update_frequency(key, tokens):
    '''
    Updates the inverted index by adding the tokens of the corpus to the dictionary and linking them to the docs
    that contain them. Other information is included such as the raw term frequency of the term within a doc, the
    the tf-idf calculated using log frequency weighing (placeholder of  log frequency value for now), and idf score
    (placeholder of freq put here for now)
    :param key: corpus
    :param tokens: dictionary of tokens and their frequency in the corpus
    '''
    doc_id = 'doc_' + key
    doc_tokens[doc_id].extend(list(tokens.keys()))

    for word, freq in tokens.items():
        #calculates log frequency weight
        log_freq = 1+math.log10(freq)
        if word in inverted:
            # inverted[word][doc_id] = {'term_freq': freq, 'tf-idf': freq}
            inverted[word][doc_id] = {'term_freq': freq, 'idf': freq, 'tf-idf': log_freq}
        else:
            #inverted[word] = {doc_id: {'term freq': freq, 'tf-idf': freq}}
            inverted[word] = {doc_id: {'term_freq': freq, 'idf': freq, 'tf-idf': log_freq}}



def tokens_per_doc(word_list):
    '''
    Calculates the frequency of each token inside of the corpus
    :param word_list: list of tokens inside of a corpus
    :return: dictionary of each token and its frequency within the corpus
    '''
    tokens = defaultdict(lambda: 0)
    for word in word_list:
        tokens[word] += 1
    return dict(tokens)



def process_tokens(book_dict):
    '''
    Processes the dictionary of corpuses to produce an inverted index for each term linked to a posting.
    This function first loops through the dictionary of corpuses and parses the html content of each of these
    corpuses. The content is then tokenized and the frequency of each token in each corpus is calculated.
    Frequency information is then calculated for the inverted index.
    :param book_dict: dictionary of corpuses
    '''
    stop_words = set(stopwords.words('english'))
    for key in book_dict:
        if key not in keys_to_avoid:
            with open(file + '/' + key) as raw_content:
                soup = BeautifulSoup(raw_content, 'lxml')
                for script in soup(["script", "style"]):
                    script.extract()
                text = soup.get_text().lower()
                # print(key)

                word_list = word_tokenize(text)
                filtered_list = [token for token in word_list if not token in stop_words]

                tokens = tokens_per_doc(filtered_list)

                update_frequency(key, tokens)

    update_idf()



def process_bookkeeping():
    '''
    Loads the json file with the dictionary of corpuses and saves the dictionary. Also saves the number of
    corpuses in the json file.
    :return: the dictionary of corpuses
    '''
    with open(file + '/bookkeeping.json') as json_data:
        data = json.load(json_data)
        global doc_num
        doc_num = len(data)
    json_data.close()
    return data



def take_input():
    '''
    Takes in user input for a search query and appends it into a query list. Continues to take input
    until the user selects 'N' to stop inputting query terms.
    :return: list of queries
    '''
    queries = []
    flag = True
    while flag:
        query = input('Please enter a search query: ')
        if query != '':
            queries.append(query.lower())
        choice = input('Do you want to enter more queries? Select Y for Yes, N for No: ')
        if choice.lower() == 'n':
            flag = False
    return queries



if __name__ == '__main__':
    queries = take_input()
    book_dict = process_bookkeeping()

    #Checks if the inverted index already exists
    if os.path.exists(database):
        with open(database) as inverted_dict:
            inverted = json.load(inverted_dict)
        inverted_dict.close()

        with open(doc_database) as doc_dict:
            doc_tokens = json.load(doc_dict)
        doc_dict.close()

    #Otherwise create inverted index by processing book_dict
    else:
        process_tokens(book_dict)

        with open('inverted_index.json', 'w') as file:
            json.dump(inverted, file)
        file.close()

        with open('doc_tokens.json', 'w') as doc_file:
            json.dump(doc_tokens, doc_file)
        doc_file.close()

    url_dict = retriever.retrieve_URLs(queries, inverted, doc_tokens, doc_num, book_dict)

    # write_to_file(url_dict)
    print_urls(url_dict)


