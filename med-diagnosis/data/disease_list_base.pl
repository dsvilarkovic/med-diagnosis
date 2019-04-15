disease(_Patient, amyotrophic_lateral_sclerosis_als, [weakness, leg_weakness, difficulty_speaking, problems_with_movement, abnormal_involuntary_movements, back_pain, difficulty_in_swallowing, hand_or_finger_weakness, leg_pain, muscle_weakness, fatigue, leg_cramps_or_spasms]).
disease(_Patient, parkinson_disease, [abnormal_involuntary_movements,problems_with_movement,disturbance_of_memory,dizziness,weakness,stiffness_all_over,leg_weakness,focal_weakness,muscle_stiffness_or_tightness,difficulty_speaking,leg_stiffness_or_tightness,fears_and_phobias]).


disease(_Patient, multiple_sclerosis, [fatigue,loss_of_sensation,headache,dizziness,problems_with_movement,weakness,leg_weakness,paresthesia,disturbance_of_memory,abnormal_involuntary_movements,leg_stiffness_or_tightness,focal_weakness]).

disease(_Patient, migraine, [headache,nausea,vomiting,dizziness,diminished_vision,disturbance_of_memory,spots_or_clouds_in_vision,symptoms_of_the_face,blindness,long_menstrual_periods]).

disease(_Patient, dementia, [disturbance_of_memory,problems_with_movement,dizziness,depressive_or_psychotic_symptoms,abnormal_involuntary_movements,paresthesia,hostile_behavior,delusions_or_hallucinations,difficulty_speaking,insomnia,disturbance_of_smell_or_taste,focal_weakness]).

disease(_Patient, meningitis, [headache,fever,vomiting,neck_pain,ache_all_over,back_pain,seizures,nausea,low_back_pain,cough,leg_pain,neck_stiffness_or_tightness]).

disease(_Patient, alzheimer_disease, [disturbance_of_memory,depressive_or_psychotic_symptoms,depression,problems_with_movement,hostile_behavior,delusions_or_hallucinations,difficulty_speaking,restlessness,irregular_appearing_scalp,fears_and_phobias,muscle_cramps,_contractures,_or_spasms,eye_burns_or_stings]).

disease(_Patient, epilepsy, [seizures,headache,abnormal_involuntary_movements,disturbance_of_memory,difficulty_speaking,lack_of_growth,stiffness_all_over,eye_moves_abnormally,muscle_weakness]).

disease(_Patient, tension_headache, [headache,neck_pain,nausea,dizziness,anxiety_and_nervousness,chills,neck_stiffness_or_tightness,painful_menstruation,painful_sinuses,symptoms_of_the_face,bleeding_from_ear,back_cramps_or_spasms]).



contains_symptom(_,[]).
contains_symptom(S, [H | T]) :- member(H, S), contains_symptom(S, T).

possible_diseases(Symptoms_list, Disease) :- disease(_, Disease, Disease_Symptom_List), contains_symptom(Disease_Symptom_List, Symptoms_list).




