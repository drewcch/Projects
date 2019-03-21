import heapq
import math
from collections import defaultdict



def calc_length(doc_ID, term_list, inverted):
    '''
    Calculates Euclidean length of a doc given all of its terms. tf-idf scores were calculated using
    raw term frequency and idf scores according to cos_sim()
    :param doc_ID: ID of the doc
    :param term_list: list of tokens for the doc
    :param inverted: dictionary for the inverted index
    :return:
    '''
    total = 0
    for term in term_list:
        # total += inverted[term][doc_ID]['tf-idf'] ** 2
        total += (inverted[term][doc_ID]['term_freq'] * inverted[term][doc_ID]['idf']) ** 2
    return math.sqrt(total)



def query_idf(query, inverted, doc_num):
    '''
    Calculates the idf scores for each query term
    :param query: list of terms in the query
    :param inverted: dictionary for the inverted index
    :param doc_num: number of docs in the corpus
    :return: dictionary matching each query term to its idf score
    '''
    idf_dict = defaultdict(float)

    #loop thru terms in query to calculate the idf scores for each term
    for term in query:
        if term in inverted:
            idf_dict[term] = math.log10(float(doc_num)/len(inverted[term]))
        else:
            idf_dict[term] = 0

    return dict(idf_dict)



def cos_sim(query, inverted, doc_tokens, doc_num):
    '''
    Calculates the cos similarity of queries with multiple terms. Raw term frequency and idf scores are used
    for weighting rather than log frequency weighting to reduce bias for smaller documents.
    :param query: list of terms in the query
    :param inverted: dictionary for the inverted index
    :param doc_tokens: dictionary mapping each doc to all of its tokens
    :param doc_num: number of docs in the corpus
    :return: The top docs based off of cos similarity calculations
    '''
    q_idf = query_idf(query, inverted, doc_num)

    scores = defaultdict(float)
    intersect_docs = set()

    #loop through terms in query in order of decreasing tf-idf value
    for term in sorted(q_idf, key=q_idf.get, reverse=True):
        #skips term in query if its idf is 0, meaning it is not in the index
        if q_idf[term] == 0:
            continue

        #copies over current set of intersected documents to another variable
        current_docs = set(intersect_docs)

        #if the set of intersected documents is initially empty
        if len(intersect_docs) == 0:
            intersect_docs.update(set(inverted[term].keys()))

        #intersected documents is not empty
        else:
            intersect_docs.intersection_update(set(inverted[term].keys()))
            # print(intersect_docs)

            #checks whether intersect_docs is now empty, meaning no intersecting documents were found
            if len(intersect_docs) == 0:
                #copies over previous intersected documents in current_docs to recover lost information
                intersect_docs = current_docs

    #loop thru terms in query, calcuating the score for each doc per query term
    for term in query:
        weight = q_idf[term]

        if weight == 0:
            continue

        for doc in intersect_docs:
            # inverted[term][doc]['tf-idf']
            scores[doc] += weight * (inverted[term][doc]['term_freq'] * inverted[term][doc]['idf'])

    #loop thru to normalize all of the doc scores
    for doc in scores:
        length = calc_length(doc, doc_tokens[doc], inverted)
        scores[doc] = scores[doc]/float(length)

    #return heap containing 10 high scoring docs
    return heapq.nlargest(10, scores, key=scores.get)



def search(queries, inverted, doc_tokens, doc_num):
    '''
    Searches for the top documents for each query in the query list
    :param queries: list of queries entered by the user
    :param inverted: dictionary for the inverted index
    :param doc_tokens: dictionary mapping each doc to all of its tokens
    :param doc_num: number of docs in the corpus
    :return: dictionary containing the top docs for every query
    '''
    query_doc_dict = defaultdict(list)
    for query in queries:
        q_list = query.split(' ')

        if len(q_list) == 1:
            # checks if query exists as a word in the inverted index
            if inverted.get(query) == None:
                query_doc_dict[query] = []
                continue

            else:
                doc_dict = inverted[query]
                query_doc_dict[query] = heapq.nlargest(10, doc_dict, key=lambda d: doc_dict[d]['tf-idf'])

        else:
            query_doc_dict[query] = cos_sim(q_list, inverted, doc_tokens, doc_num)

    return query_doc_dict



def retrieve_URLs(queries, inverted, doc_tokens, doc_num, book_dict):
    '''
    Retrieves URLs using the dictionary containing the top documents for each query
    :param queries: list of queries entered by the user
    :param inverted: dictionary for the inverted index
    :param doc_tokens: dictionary mapping each doc to all of its tokens
    :param doc_num: number of docs in the corpus
    :param book_dict: dictionary from json file matching each doc to its URL
    :return: dictionary of URLs matched to each query
    '''

    query_doc_dict = search(queries, inverted, doc_tokens, doc_num)
    url_dict = defaultdict(list)

    for query, doc_list in query_doc_dict.items():

        if len(doc_list) == 0:
            url_dict[query] = []
            continue

        for doc in doc_list:
            id = doc[4:]
            url_dict[query].append(book_dict[id])

    return dict(url_dict)