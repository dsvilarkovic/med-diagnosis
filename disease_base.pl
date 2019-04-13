disease(Patient, parkinson_disease):-
	 symptom(Patient, abnormal_involuntary_movements),
	 symptom(Patient, problems_with_movement),
	 symptom(Patient, disturbance_of_memory),
	 symptom(Patient, dizziness),
	 symptom(Patient, weakness),
	 symptom(Patient, stiffness_all_over),
	 symptom(Patient, leg_weakness),
	 symptom(Patient, focal_weakness),
	 symptom(Patient, muscle_stiffness_or_tightness),
	 symptom(Patient, difficulty_speaking),
	 symptom(Patient, leg_stiffness_or_tightness),
	 symptom(Patient, fears_and_phobias).

disease(Patient, meningitis):-
	 symptom(Patient, headache),
	 symptom(Patient, fever),
	 symptom(Patient, vomiting),
	 symptom(Patient, neck_pain),
	 symptom(Patient, ache_all_over),
	 symptom(Patient, back_pain),
	 symptom(Patient, seizures),
	 symptom(Patient, nausea),
	 symptom(Patient, low_back_pain),
	 symptom(Patient, cough),
	 symptom(Patient, leg_pain),
	 symptom(Patient, neck_stiffness_or_tightness).


disease(Patient, multiple_sclerosis):-
	 symptom(Patient, fatigue),
	 symptom(Patient, loss_of_sensation),
	 symptom(Patient, headache),
	 symptom(Patient, dizziness),
	 symptom(Patient, problems_with_movement),
	 symptom(Patient, weakness),
	 symptom(Patient, leg_weakness),
	 symptom(Patient, paresthesia),
	 symptom(Patient, disturbance_of_memory),
	 symptom(Patient, abnormal_involuntary_movements),
	 symptom(Patient, leg_stiffness_or_tightness),
	 symptom(Patient, focal_weakness).


disease(Patient, migraine):-
	 symptom(Patient, headache),
	 symptom(Patient, nausea),
	 symptom(Patient, vomiting),
	 symptom(Patient, dizziness),
	 symptom(Patient, diminished_vision),
	 symptom(Patient, disturbance_of_memory),
	 symptom(Patient, spots_or_clouds_in_vision),
	 symptom(Patient, symptoms_of_the_face),
	 symptom(Patient, blindness),
	 symptom(Patient, long_menstrual_periods).


disease(Patient, dementia):-
	 symptom(Patient, disturbance_of_memory),
	 symptom(Patient, problems_with_movement),
	 symptom(Patient, dizziness),
	 symptom(Patient, depressive_or_psychotic_symptoms),
	 symptom(Patient, abnormal_involuntary_movements),
	 symptom(Patient, paresthesia),
	 symptom(Patient, hostile_behavior),
	 symptom(Patient, delusions_or_hallucinations),
	 symptom(Patient, difficulty_speaking),
	 symptom(Patient, insomnia),
	 symptom(Patient, disturbance_of_smell_or_taste),
	 symptom(Patient, focal_weakness).


disease(Patient, meningitis):-
	 symptom(Patient, headache),
	 symptom(Patient, fever),
	 symptom(Patient, vomiting),
	 symptom(Patient, neck_pain),
	 symptom(Patient, ache_all_over),
	 symptom(Patient, back_pain),
	 symptom(Patient, seizures),
	 symptom(Patient, nausea),
	 symptom(Patient, low_back_pain),
	 symptom(Patient, cough),
	 symptom(Patient, leg_pain),
	 symptom(Patient, neck_stiffness_or_tightness).


disease(Patient, alzheimer_disease):-
	 symptom(Patient, disturbance_of_memory),
	 symptom(Patient, depressive_or_psychotic_symptoms),
	 symptom(Patient, depression),
	 symptom(Patient, problems_with_movement),
	 symptom(Patient, hostile_behavior),
	 symptom(Patient, delusions_or_hallucinations),
	 symptom(Patient, difficulty_speaking),
	 symptom(Patient, restlessness),
	 symptom(Patient, irregular_appearing_scalp),
	 symptom(Patient, fears_and_phobias),
	 symptom(Patient, muscle_cramps,contractures_or_spasms),
	 symptom(Patient, eye_burns_or_stings).


disease(Patient, epilepsy):-
	 symptom(Patient, seizures),
	 symptom(Patient, headache),
	 symptom(Patient, abnormal_involuntary_movements),
	 symptom(Patient, disturbance_of_memory),
	 symptom(Patient, difficulty_speaking),
	 symptom(Patient, lack_of_growth),
	 symptom(Patient, stiffness_all_over),
	 symptom(Patient, eye_moves_abnormally),
	 symptom(Patient, muscle_weakness).


disease(Patient, tension_headache):-
	 symptom(Patient, headache),
	 symptom(Patient, neck_pain),
	 symptom(Patient, nausea),
	 symptom(Patient, dizziness),
	 symptom(Patient, anxiety_and_nervousness),
	 symptom(Patient, chills),
	 symptom(Patient, neck_stiffness_or_tightness),
	 symptom(Patient, painful_menstruation),
	 symptom(Patient, painful_sinuses),
	 symptom(Patient, symptoms_of_the_face),
	 symptom(Patient, bleeding_from_ear),
	 symptom(Patient, back_cramps_or_spasms).

disease(Patient, amyotrophic_lateral_sclerosis_als):-
	 symptom(Patient, weakness),
	 symptom(Patient, leg_weakness),
	 symptom(Patient, difficulty_speaking),
	 symptom(Patient, problems_with_movement),
	 symptom(Patient, abnormal_involuntary_movements),
	 symptom(Patient, back_pain),
	 symptom(Patient, difficulty_in_swallowing),
	 symptom(Patient, hand_or_finger_weakness),
	 symptom(Patient, leg_pain),
	 symptom(Patient, muscle_weakness),
	 symptom(Patient, fatigue),
	 symptom(Patient, leg_cramps_or_spasms).



