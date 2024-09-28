from openai import OpenAI
import os
from dotenv import load_dotenv
import json
load_dotenv()

client = OpenAI(api_key=os.getenv("API_KEY"))

completion = client.chat.completions.create(
    model="gpt-4o-mini",
    messages=[
        {"role": "system", "content": "You are a professor who is aiding students in their desired topics in order for them to better understand it please refer to the institution and course they provided. In the explanation provide 2-3 links to outside resources for their course. After an explanation provide 1 multiple choice practice problem with no less than 3 choices and explain why which is the correct answer. The explanation, multiple choice question, and the answer explanation should be seperated by 'spc' for easier formatting"},
        {
            "role": "user",
            "content": "Institution: Florida Internation Univeristy | Course: MAC2311 | Topic: Equation of tangent line using limit definition of derivative"
        }
    ]
)

output = completion.choices[0].message.content.split("spc")

print(completion.choices[0].message.content)

with open("output.json", "r+") as file:
    data = json.load(file)
    data['Response']['Explanation'] = output[0]
    data['Response']['Question'] = output[1]
    data['Response']['Answer'] = output[-1]
    file.seek(0)
    file.truncate(0)
    file.write(json.dumps(data))