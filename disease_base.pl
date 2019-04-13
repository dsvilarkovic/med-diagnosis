disease(Patient, parkinson_disease):-
	 symptom_percentage(Patient, abnormal_involuntary_movements),
	 symptom_percentage(Patient, problems_with_movement),
	 symptom_percentage(Patient, disturbance_of_memory),
	 symptom_percentage(Patient, dizziness),
	 symptom_percentage(Patient, weakness),
	 symptom_percentage(Patient, stiffness_all_over),
	 symptom_percentage(Patient, leg_weakness),
	 symptom_percentage(Patient, focal_weakness),
	 symptom_percentage(Patient, muscle_stiffness_or_tightness),
	 symptom_percentage(Patient, difficulty_speaking),
	 symptom_percentage(Patient, leg_stiffness_or_tightness),
	 symptom_percentage(Patient, fears_and_phobias).

disease(Patient, meningitis):-
	 symptom_percentage(Patient, headache),
	 symptom_percentage(Patient, fever),
	 symptom_percentage(Patient, vomiting),
	 symptom_percentage(Patient, neck_pain),
	 symptom_percentage(Patient, ache_all_over),
	 symptom_percentage(Patient, back_pain),
	 symptom_percentage(Patient, seizures),
	 symptom_percentage(Patient, nausea),
	 symptom_percentage(Patient, low_back_pain),
	 symptom_percentage(Patient, cough),
	 symptom_percentage(Patient, leg_pain),
	 symptom_percentage(Patient, neck_stiffness_or_tightness).


disease(Patient, multiple_sclerosis):-
	 symptom_percentage(Patient, fatigue),
	 symptom_percentage(Patient, loss_of_sensation),
	 symptom_percentage(Patient, headache),
	 symptom_percentage(Patient, dizziness),
	 symptom_percentage(Patient, problems_with_movement),
	 symptom_percentage(Patient, weakness),
	 symptom_percentage(Patient, leg_weakness),
	 symptom_percentage(Patient, paresthesia),
	 symptom_percentage(Patient, disturbance_of_memory),
	 symptom_percentage(Patient, abnormal_involuntary_movements),
	 symptom_percentage(Patient, leg_stiffness_or_tightness),
	 symptom_percentage(Patient, focal_weakness).


disease(Patient, migraine):-
	 symptom_percentage(Patient, headache),
	 symptom_percentage(Patient, nausea),
	 symptom_percentage(Patient, vomiting),
	 symptom_percentage(Patient, dizziness),
	 symptom_percentage(Patient, diminished_vision),
	 symptom_percentage(Patient, disturbance_of_memory),
	 symptom_percentage(Patient, spots_or_clouds_in_vision),
	 symptom_percentage(Patient, symptoms_of_the_face),
	 symptom_percentage(Patient, blindness),
	 symptom_percentage(Patient, long_menstrual_periods).


disease(Patient, dementia):-
	 symptom_percentage(Patient, disturbance_of_memory),
	 symptom_percentage(Patient, problems_with_movement),
	 symptom_percentage(Patient, dizziness),
	 symptom_percentage(Patient, depressive_or_psychotic_symptoms),
	 symptom_percentage(Patient, abnormal_involuntary_movements),
	 symptom_percentage(Patient, paresthesia),
	 symptom_percentage(Patient, hostile_behavior),
	 symptom_percentage(Patient, delusions_or_hallucinations),
	 symptom_percentage(Patient, difficulty_speaking),
	 symptom_percentage(Patient, insomnia),
	 symptom_percentage(Patient, disturbance_of_smell_or_taste),
	 symptom_percentage(Patient, focal_weakness).


disease(Patient, meningitis):-
	 symptom_percentage(Patient, headache),
	 symptom_percentage(Patient, fever),
	 symptom_percentage(Patient, vomiting),
	 symptom_percentage(Patient, neck_pain),
	 symptom_percentage(Patient, ache_all_over),
	 symptom_percentage(Patient, back_pain),
	 symptom_percentage(Patient, seizures),
	 symptom_percentage(Patient, nausea),
	 symptom_percentage(Patient, low_back_pain),
	 symptom_percentage(Patient, cough),
	 symptom_percentage(Patient, leg_pain),
	 symptom_percentage(Patient, neck_stiffness_or_tightness).


disease(Patient, alzheimer_disease):-
	 symptom_percentage(Patient, disturbance_of_memory),
	 symptom_percentage(Patient, depressive_or_psychotic_symptoms),
	 symptom_percentage(Patient, depression),
	 symptom_percentage(Patient, problems_with_movement),
	 symptom_percentage(Patient, hostile_behavior),
	 symptom_percentage(Patient, delusions_or_hallucinations),
	 symptom_percentage(Patient, difficulty_speaking),
	 symptom_percentage(Patient, restlessness),
	 symptom_percentage(Patient, irregular_appearing_scalp),
	 symptom_percentage(Patient, fears_and_phobias),
	 symptom_percentage(Patient, muscle_cramps,contractures_or_spasms),
	 symptom_percentage(Patient, eye_burns_or_stings).


disease(Patient, epilepsy):-
	 symptom_percentage(Patient, seizures),
	 symptom_percentage(Patient, headache),
	 symptom_percentage(Patient, abnormal_involuntary_movements),
	 symptom_percentage(Patient, disturbance_of_memory),
	 symptom_percentage(Patient, difficulty_speaking),
	 symptom_percentage(Patient, lack_of_growth),
	 symptom_percentage(Patient, stiffness_all_over),
	 symptom_percentage(Patient, eye_moves_abnormally),
	 symptom_percentage(Patient, muscle_weakness).


disease(Patient, tension_headache):-
	 symptom_percentage(Patient, headache),
	 symptom_percentage(Patient, neck_pain),
	 symptom_percentage(Patient, nausea),
	 symptom_percentage(Patient, dizziness),
	 symptom_percentage(Patient, anxiety_and_nervousness),
	 symptom_percentage(Patient, chills),
	 symptom_percentage(Patient, neck_stiffness_or_tightness),
	 symptom_percentage(Patient, painful_menstruation),
	 symptom_percentage(Patient, painful_sinuses),
	 symptom_percentage(Patient, symptoms_of_the_face),
	 symptom_percentage(Patient, bleeding_from_ear),
	 symptom_percentage(Patient, back_cramps_or_spasms).

disease(Patient, amyotrophic_lateral_sclerosis_als):-
	 symptom_percentage(Patient, weakness),
	 symptom_percentage(Patient, leg_weakness),
	 symptom_percentage(Patient, difficulty_speaking),
	 symptom_percentage(Patient, problems_with_movement),
	 symptom_percentage(Patient, abnormal_involuntary_movements),
	 symptom_percentage(Patient, back_pain),
	 symptom_percentage(Patient, difficulty_in_swallowing),
	 symptom_percentage(Patient, hand_or_finger_weakness),
	 symptom_percentage(Patient, leg_pain),
	 symptom_percentage(Patient, muscle_weakness),
	 symptom_percentage(Patient, fatigue),
	 symptom_percentage(Patient, leg_cramps_or_spasms).



