from openai import OpenAI
import os
from dotenv import load_dotenv
import json
from googleapiclient.discovery import build
load_dotenv()

client = OpenAI(api_key=os.getenv("OPENAI_API_KEY"))
with open("src/SHELLHACKS_2024_PROJECT/data.json", "r+") as input:
    data = json.load(input)
    school = data["school"]
    course = data["course"]
    topic = data["topic"]

def default_ai():
    completion = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": "You are a professor who is aiding students in their desired topics in order for them to better understand it please refer to the institution and course they provided. After an explanation work out a practice problem and make the answer of the problem a choice in a 4 option multiple choice for that practice problem. Make sure the explanation, multiple choice question, and the answer explanation are seperated by 'spc' and all '\' should be replaced by '$' for easier formatting. Also seperated by 'spc' put the letter of the answer alone at the bottom"},
            {
                "role": "user",
                "content": f"Institution: {school} | Course: {course} | Topic: {topic}"
            } 
        ]
    )
    output = completion.choices[0].message.content.split("spc")

    return output[0]

def followup_ai():
    with open("src/SHELLHACKS_2024_PROJECT/input.json", "r+") as input:
        data = json.load(input)
        input = data["Input"]
        completion = client.chat.completions.create(
            model="gpt-4o-mini",
            messages=[
                {"role": "system", "content": "You are a professor who is aiding students in their desired topics in order for them to better understand it. You are here to help them interpret your past responses to their questions and follow up on any more questions they have make sure that all '\' in your response should be replaced by '$' for easier formatting."},
                {
                    "role": "user",
                    "content": f"You responded with {default_ai()}, they are asking now {input}"
                } 
            ]
        )
        output = completion.choices[0].message.content

    with open("src/SHELLHACKS_2024_PROJECT/output.json", "r+") as file:
        data = json.load(file)
        data['Response']['Output'] = output

followup_ai()