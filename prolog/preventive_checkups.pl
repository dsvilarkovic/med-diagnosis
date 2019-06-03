%ALS

%Riluzole is taken as a pill and may cause side effects such as dizziness, gastrointestinal conditions and liver function changes.
preventive_examinations(amyotrophic_lateral_sclerosis_als, [riluzole], 'examination_for_liver_function_changes').
preventive_examinations(amyotrophic_lateral_sclerosis_als, [riluzole], 'examination_for_gastrointestinal_conditions').

%Over time, ALS paralyzes the muscles you use to breathe, Some people with advanced ALS choose to have a tracheostomy
preventive_examinations(amyotrophic_lateral_sclerosis_als, [], 'tracheostomy').

%some are eventually diagnosed with a form of dementia called frontotemporal dementia
preventive_examinations(amyotrophic_lateral_sclerosis_als, [], 'examination_for_frontotemporal_dementia') .

%People with ALS are at higher risk of getting food, liquids or saliva into the lungs, which can cause pneumonia. 
preventive_examinations(amyotrophic_lateral_sclerosis_als, [], 'examination_for_pneumonia').

%TENSION HEADACHE

%Certain pain relievers called nonsteroidal anti-inflammatory drugs (NSAIDs), such as ibuprofen (Advil, Motrin IB, others) may cause abdominal pain, bleeding, ulcers and other complications
preventive_examinations(tension_headache, [diclofenac_topical_product, piroxicam], 'examination_for_ulcer').

% Occasionally, headaches may indicate a serious medical condition, such as a brain tumor or rupture of a weakened blood vessel (aneurysm).
preventive_examinations(tension_headache, [], 'examination_for_brain_tumor').
preventive_examinations(tension_headache, [], 'examination_for_aneurysm').

%MIGRAINE

%Abnormal activity of serotonin has been implicated in both migraine and depression, and medications that modify serotonin can be effective in treating both disorders. 
preventive_examinations(migraine, [sumatriptan_(imitrex)], 'examination_for_serotonin_syndrome').

%PARKINSON DISEASE

%Bladder problems. Parkinsons disease may cause bladder problems, including being unable to control urine or having difficulty urinating.
preventive_examinations(parkinson_disease, [], 'urologist_examination_for_bladder_problems').

%Autoimmune factors: Scientists reported in JAMA in 2017 that they had found evidence of a possible genetic link between PD and autoimmune rheumatic diseases
preventive_examinations(parkinson_disease, [], 'examination_for_autoimmune_rheumatic_diseases').

%A person with PD may have clumps of protein in their brain known as Lewy bodies. Lewy body dementia is a different condition, but it has links with PD.
preventive_examinations(parkinson_disease, [], 'examination_for_lewy_body_dementia').

%DEMENTIA 

%Other disorders linked to dementia: Huntingtons disease, Traumatic brain injury (TBI), Creutzfeldt-Jakob disease, Parkinsons disease.
preventive_examinations(dementia, [], 'examination_for_huntington_disease').
preventive_examinations(dementia, [], 'examination_for_traumatic_brain_injury').
preventive_examinations(dementia, [], 'examination_for_creutzfeldt_jakom_disease').
preventive_examinations(dementia, [], 'examination_for_parkinson_disease').

%High blood pressure might lead to a higher risk of some types of dementia.
preventive_examinations(dementia, [], 'examination_for_high_blood_preasure').

%MULTIPLE SCLEROSIS

%Corticosteroids - side effects may include insomnia, increased blood pressure, mood swings and fluid retention.
preventive_examinations(multiple_sclerosis, [azathioprine], 'examination_for_high_blood_preasure').

%You ll need blood tests to monitor your liver enzymes because liver damage is a possible side effect of interferon use
preventive_examinations(multiple_sclerosis, [interferon_beta_1a_(avonex)], 'examination_for_liver_damadge').

%This medication increases the risk of a potentially serious viral infection of the brain called progressive multifocal leukoencephalopathy (PML) in people who are positive for antibodies to the causative agent of PML JC virus.
preventive_examinations(multiple_sclerosis, [natalizumab_(tysabri)], 'examination_for_multifocal_leukoencephalopathy').

%Vision problems are also common
preventive_examinations(multiple_sclerosis, [], 'ophthalmologist_examination_for_vision_problems').

%ALZHEIMER DISEASE

%Cholinesterase inhibitors - In people with cardiac conduction disorders, serious side effects may include cardiac arrhythmia.
preventive_examinations(alzheimer_disease, [donepezil_(aricept), rivastigmine_(exelon), galantamine], 'examination_for_cardiac_arrhythmia').

%As Alzheimers disease progresses to its last stages, brain changes begin to affect physical functions; hese effects can increase vulnerability to additional health problems
preventive_examinations(alzheimer_disease, [], 'examination_for_pneumonia').

%EPILEPSY

%Anti-seizure medications may have some side effects - Inflammation of certain organs, such as your liver
preventive_examinations(epilepsy, [levetiracetam_(keppra),phenytoin_(dilantin), lamotrigine_(lamictal), divalproex_sodium_(depakote), carbamazepine, topiramate_(topamax), phenobarbital, zonisamide, valproic_acid, fosphenytoin, ethosuximide, lacosamide_(vimpat)], 'examination_for_liver_inflamamation').

%Brain conditions that cause damage to the brain, such as brain tumors or strokes, can cause epilepsy. Stroke is a leading cause of epilepsy in adults older than age 35.
preventive_examinations(epilepsy, [], 'examination_for_brain_damages').

%Infectious diseases. Infectious diseases, such as meningitis, AIDS and viral encephalitis, can cause epilepsy.
preventive_examinations(epilepsy, [], 'examination_for_infectious_diseases').

%Head trauma. Head trauma as a result of a car accident or other traumatic injury can cause epilepsy.
preventive_examinations(epilepsy, [], 'examination_for_head_traumas').

%MENINGITIS

%Meningitis can also result from noninfectious causes, such as chemical reactions, drug allergies, some types of cancer and inflammatory diseases such as sarcoidosis.
preventive_examinations(meningitis, [], 'examination_for_inflammatory_diseases').

%Because impaired hearing is a common complication, those whove had bacterial meningitis should have a hearing test after they recover.
preventive_examinations(meningitis, [], 'hearing_test').

%Complications of bacterial meningitis might need extra treatment. Someone with shock or low blood pressure might get more IV fluids and medicines to increase blood pressure. Some kids may need extra oxygen or mechanical ventilation if they have trouble breathing.
preventive_examinations(meningitis, [], 'examination_for_low_blood_preasure').
preventive_examinations(meningitis, [], 'examination_for_low_breathing_problems').




contains([],[]).
contains([H|T],S) :- member(H,S), !.
contains([H|T],S) :- contains(T,S).


recommended_preventive_examinations(Disease, Medication, Result) :- preventive_examinations(Disease, ResultMedication, Result), contains(Medication,ResultMedication).

