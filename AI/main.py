from openai import OpenAI
import os
from dotenv import load_dotenv
import json
from googleapiclient.discovery import build
load_dotenv()

client = OpenAI(api_key=os.getenv("OPENAI_API_KEY"))


completion = client.chat.completions.create(
    model="gpt-4o-mini",
    messages=[
        {"role": "system", "content": "You are a professor who is aiding students in their desired topics in order for them to better understand it please refer to the institution and course they provided. After an explanation work out a practice problem and make the answer of the problem a choice in a 4 option multiple choice for that practice problem. The explanation, multiple choice question, and the answer explanation should be seperated by 'spc' for easier formatting"},
        {
            "role": "user",
            "content": "Institution: Florida International Univeristy | Course: MAC2311 | Topic: Equation of tangent line using limit definition of derivative"
        } #replace institution, course, topic with input corresponding
    ]
)

def search_videos(query):
    youtube = build('youtube', 'v3', developerKey=os.getenv("GOOGLE_API_KEY"))
    request = youtube.search().list(part='snippet', type='video', q=query, maxResults=3)
    response = request.execute()
    return response

query = 'python tutorials' #replace with input topic
results = search_videos(query)
videos = []
for i in range(len(results["items"])):
    videos.append((f"https://www.youtube.com/watch?v={results['items'][i]['id']['videoId']}", results["items"][i]['snippet']['title']))


output = completion.choices[0].message.content.split("spc")

print(completion.choices[0].message.content)

with open("SHELLHACKS_2024_PROJECT\output.json", "r+") as file:
    data = json.load(file)
    data['Response']['Explanation'] = f"{output[0]}(Extra Resources): {videos[0][0]} ({videos[0][-1]})\n{videos[1][0]} ({videos[1][-1]})\n{videos[-1][0]} ({videos[-1][-1]})\n\n"
    data['Response']['Question'] = output[1]
    data['Response']['Answer'] = output[-1]
    file.seek(0)
    file.truncate(0)
    file.write(json.dumps(data))