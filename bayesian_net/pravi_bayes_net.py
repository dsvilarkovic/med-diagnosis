f = open("novo_nesto.txt", "r")
disease_file = open("disease_base.pl")

header = "net \"Nova_mreza\"\n\
{ \n\
     node_size = (0 0); \n\
     name = \"New BN\"; \n\
     UnBBayes_Color_Probabilistic_Description = \"-256\"; \n\
     UnBBayes_Color_Probabilistic_Explanation = \"-16711936\"; \n\
};\n"

symptom_nodes = ""
symptom_potentials = ""
all_symptom_list = []
conditional_symptom_nodes = {}
newline=  "\n"
k = 1
x_position = 300
for line in f:
    text_line = line
    text_line = text_line.replace(" ", "_").replace("\n", "")
    
    symptom_variable = f"node {text_line}\
                        {{   {newline}\
                            label = \"C{k}\"; {newline}\
                            position = ({x_position} 35); {newline}\
                            states = (\"Da\", \"Ne\");   {newline}\
                        }}"

    # symptom_potential = f"potential ({text_line})\
    #                     {{ {newline}\
    #                       data = ( 0.5 0.5 ); {newline}\
    #                     }} \
    #                     "
    all_symptom_list.append(text_line)

    symptom_nodes = symptom_nodes + symptom_variable + "\n"
    # symptom_potentials = symptom_potentials + symptom_potential + "\n"

    k = k + 1
    x_position = x_position + 100

x_position = 500
disease_potentials = ""
disease_nodes = ""
disease_name = ""

for line in disease_file:
    if "disease" in line:
        tokens = line.split(",")
        disease_name = tokens[1].replace("):-", "").strip()
        #print(disease_name)

        disease_variable = f"node {disease_name}\
                            {{   {newline}\
                                label = \"C{k}\"; {newline}\
                                position = ({x_position} 300); {newline}\
                                states = (\"Da\", \"Ne\");   {newline}\
                            }}"

        disease_potential = f"potential ({disease_name})\
                            {{ {newline}\
                            data = ( 0.5 0.5 ); {newline}\
                            }} \
                            "

        disease_nodes = disease_nodes + disease_variable + "\n"
        disease_potentials = disease_potentials + disease_potential + "\n"

        x_position = x_position + 100
        k = k + 1


    if "symptom" in line:
        tokens = line.split(",")
        number = tokens[2].replace(")", "")
        number = number.split(".")[0] + number.split(".")[1]

        percentage = float(number) / 100
        symptom_name = line.split(",")[1].strip()

        symptom_potential = f"potential ({symptom_name} | {disease_name})\
                            {{ {newline}\
                            data = (( 0.5 0.5 ); {newline}\
                            }} \
                            "

        key = f"{symptom_name}" + "disease_conditions"
        if(key not in conditional_symptom_nodes.keys()):
            conditional_symptom_nodes[key] = ""
        conditional_symptom_nodes[key] += disease_name + " "

        key = f"{symptom_name}" + "disease_data"
        if(key not in conditional_symptom_nodes.keys()):
            conditional_symptom_nodes[key] = []
        conditional_symptom_nodes[key].append(f"({percentage} {1 - percentage})")


conditional_symptoms = ""
for symptom_name in all_symptom_list:
    key = f"{symptom_name}" + "disease_conditions"
    symptom_disease_list = conditional_symptom_nodes[key]

    key = f"{symptom_name}" + "disease_data"
    #find all conditional diseases data
    diseases_length = len(conditional_symptom_nodes[key])

    #print(conditional_symptom_nodes[key])

    data_list = ["(0.5 0.5)"] * 2 ** diseases_length
  

    for idx, data_item in enumerate(conditional_symptom_nodes[key]):
        binarni_niz = ["1"] * diseases_length
        binarni_niz[idx] = "0"
        binary_string = "".join(binarni_niz)
        binary_number = int(binary_string, 2)   
        data_item_index = binary_number

        data_list[data_item_index] = data_item

    data = " ".join(data_list)

    data = f"(\n{data}\n);"
    # print(data)

    key = f"{symptom_name}" + "disease_conditions"
    disease_data = conditional_symptom_nodes[key]
    symptom_conditionals_node = f"potential ({symptom_name} | {disease_data})\
                            {{ {newline}\
                            data = {data} {newline}\
                            }} \
                            "

    print(symptom_conditionals_node)

    conditional_symptoms += symptom_conditionals_node + "\n"


write_f = open("mreza.net", "a")
write_f.write(header)
write_f.write(symptom_nodes)
write_f.write(disease_nodes)
# write_f.write(symptom_potentials)
write_f.write(disease_potentials)
write_f.write(conditional_symptoms)

write_f.close()
f.close()

