import os
from pinecone.grpc import PineconeGRPC as Pinecone
from langchain_openai import OpenAIEmbeddings 
from langchain_pinecone import PineconeVectorStore 
from langchain_openai import ChatOpenAI  
from langchain.chains import RetrievalQA
from dotenv import load_dotenv

load_dotenv()
index_name = os.environ.get("PINECONE_INDEX")  

pc = Pinecone()
index = pc.Index(index_name) 

model_name = 'text-embedding-3-small'  

embeddings = OpenAIEmbeddings(  
    model=model_name,  
)


from langchain_pinecone import PineconeVectorStore 

text_field = "text"  
vectorstore = PineconeVectorStore(  
    index, embeddings, text_field  
) 

llm = ChatOpenAI(  
    openai_api_key=os.environ.get("OPENAI_API_KEY"),  
    model_name='gpt-4.1',  
    temperature=0.0  
)  

qa = RetrievalQA.from_chain_type(  
    llm=llm,  
    chain_type="stuff",  
    retriever=vectorstore.as_retriever()  
) 


from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route('/response', methods=['POST'])
def query_data():
    data = request.json
    query = data.get('query', 0)
    queryin = "You are teaching modules to a caregiver who is learning how to take care of a the elderly. When answering questions, answer in easy to understand and concise sentences, using real life examples when applicable. If questions are asked not involving caregiver information answer with the following: I can't answer that question at this time. The query is: " + query
    response = qa.invoke(queryin)["result"]
    result = response
    return jsonify({'result': result})


@app.route('/quizresponse', methods=['POST'])
def quiz_data():
    data = request.json
    query = data.get('query', 0)
    response = qa.invoke(query)["result"]
    result = response
    return jsonify({'result': result})


@app.route('/incorrectAnswers', methods=['POST'])
def incorrect_data():
    data = request.json
    query = data.get('query', 0)
    queryin = "Create 3 incorrect answers for each of the 5 following questions and correct answers: " + query + " Make the 3 incorrect answers quite obviously incorrect but include no violence, ensure the incorrect answers are incorrect in someway. The questions will afterwards have a total of 4 choices (A-D), ensure there is only be one correct answer and it is the exact one given, have no questions correct answer be 'all of the above'. Start response by listing each question starting with 'Q1:' followed by the question text then after listing the question, list each possible Answer for the question beginning each one A-D like 'A:' followed by the answer text. After listing all of the questions and answers give the correct answers formatted like so: 'Answers: Q1: A, Q2: B...';. Mix up which Letter holds the correct answer from A to D. ENSURE THE CORRECT ANSWER IS LISTED CORRECTLY AND IS THE ONE GIVEN. Do not have any other text other than questions, answers, and correct answer list"
    response = llm.invoke(queryin)
    result = response.content
    return jsonify({'result': result})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=80, debug=False)  # The server will listen on port 5000 