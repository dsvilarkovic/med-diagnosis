from bs4 import BeautifulSoup
import requests
import re
import sys

def lowerCaseAndUnderline(string_value):
    """Converts values like string 'Ana Voli Milovana' to 'ana_voli_milovana'"""
    return string_value.lower().replace(' ', '_').replace('-', '_')



def main(DISEASE_TOKEN, isRequestEnabled = False):

    if(isRequestEnabled):
        result = requests.get("http://www.symcat.com/conditions/" + DISEASE_TOKEN)

        if(result.status_code == 200):
            content = result.content
            file = open("response_text.txt", "w")
            file.write(str(result.content, 'utf-8'))
            file.close()
    else:
        file = open("response_text.txt", "r")
        content = file.read()
        file.close()

    soup = BeautifulSoup(content, features = 'lxml')


    #taking symptoms
    final = soup.findAll("span", itemprop = "signOrSymptom")


    disease_data = ""
    symptom_names_block = ""
    symptom_percentages_block = ""
    disease_name = lowerCaseAndUnderline(DISEASE_TOKEN)
    for item in final:

        #gets name of the symptom e.g. Dizziness
        symptom_name = item.find("span", itemprop = "name").text
        print(symptom_name)
        symptom_name = lowerCaseAndUnderline(symptom_name)
        print(symptom_name)
        #gets change percentage of the symptom e.g. 20%
        symptom_percentage = item.find("span", "pct-box").text
        print(symptom_percentage + "%")

        
        symptom_names_block += f"{symptom_name}({disease_name})." + "\n" 
        symptom_percentages_block +=  f"symptom_percentage({disease_name}, {symptom_name}, {symptom_percentage})." + "\n" 


    disease_data += symptom_names_block
    disease_data += "\n\n"
    disease_data += symptom_percentages_block
    
    text_file = open("symptoms_base.pl", "w")
    text_file.write(disease_data)
    text_file.close()

    #taking common tests and procedures

    tests_and_procedures = soup.find("div", id = "proc_chart_div")
    #all rows containing each procedure
    table_rows = tests_and_procedures.findAll("tr")

    procedures_block = ""

    for tr in table_rows:
        #get procedure name
        procedure = tr.find("td", "chart-label").find("a").text
        print(procedure)
        procedure = lowerCaseAndUnderline(procedure)
        print(procedure)
        
        #get procedure likeliness to succeed in percentage
        success_percentage = tr.find("div", "bar-fill")["style"]
        success_percentage = success_percentage.split(":")[1]
        success_percentage = float(success_percentage[:-2])
        print(success_percentage)

        procedures_block += f"recommended_procedure({disease_name}, '{procedure}', {success_percentage}).\n"

    text_file = open("procedures_base.pl", "w")
    text_file.write(procedures_block)
    text_file.close()


    #now recommended medications
    medics = soup.find("div", id = "med_chart_div")
    table_rows = medics.findAll("tr")

    medications_block = ""

    for tr in table_rows:
        medication = tr.find("td", "chart-label").find("a").text
        print(medication)
        medication = lowerCaseAndUnderline(medication)

        success_percentage = tr.find("div", "bar-fill")["style"]
        success_percentage = success_percentage.split(":")[1]
        success_percentage = float(success_percentage[:-2])
        print(success_percentage)
        
        medications_block += f"recommended_medication({disease_name}, '{medication}', {success_percentage}).\n"

    text_file = open("medications_base.pl", "w")
    text_file.write(medications_block)
    text_file.close()


    #demographics likeliness

    # demographics = soup.find("div", id = "demographic-risks")

    # #Age

    # age_tag = demographics.find("h3", text = re.compile("Age"))
    # age_table = age_tag.find_next("table", "risk-chart")
    
    # table_rows = age_table.findAll("tr")

    # for tr in table_rows:
    #     bar = tr.find("div", "bar-fill-")


if(__name__ == "__main__"):

    if(len(sys.argv) >= 2):
        scrape = False if sys.argv[1] == "-s" else True
    else:
        scrape = True

    if(len(sys.argv[0]) > 0):
        disease_name = str(sys.argv[0])
    else:
        disease_name = "parkinson-disease"


    main(disease_name, scrape)