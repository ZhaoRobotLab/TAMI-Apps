import requests
import json
#server url of locally hosted server must be changed to match the server url of the server that is running the server.py file
server_url = 'NGROK_URL'

def response(query):
    data = {'query': query}
    response = requests.post(server_url+'/response', data=json.dumps(data), headers={'Content-Type': 'application/json'}, verify=False)
    if response.status_code == 200:
        result = response.json().get('result')
        return(result)
    else:
        return("Failed to get a valid response from the server.")

def slide(query):
    data = {'query': query}
    response = requests.post(server_url+'/slide', data=json.dumps(data), headers={'Content-Type': 'application/json'}, verify=False)
    if response.status_code == 200:
        result = response.json().get('result')
        return(result)
    else:
        return("Failed to get a valid response from the server.")


def quizresponse(query): 
    data = {'query': query}
    response = requests.post(server_url+'/quizresponse', data=json.dumps(data), headers={'Content-Type': 'application/json'}, verify=False)
    if response.status_code == 200:
        result = response.json().get('result')
        return(result)
    else:
        return("Failed to get a valid response from the server.")

def incorrectAnswers(query):
    data = {'query': query}
    response = requests.post(server_url+'/incorrectAnswers', data=json.dumps(data), headers={'Content-Type': 'application/json'}, verify=False)
    if response.status_code == 200:
        result = response.json().get('result')
        return(result)
    else:
        return("Failed to get a valid response from the server.")