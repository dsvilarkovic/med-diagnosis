disease(Patient, parkinson_disease):-
	 symptom(Patient, abnormal_involuntary_movements, 0.64),
	 symptom(Patient, problems_with_movement, 0.41),
	 symptom(Patient, disturbance_of_memory, 0.28),
	 symptom(Patient, dizziness, 0.25),
	 symptom(Patient, weakness, 0.22),
	 symptom(Patient, stiffness_all_over, 0.07),
	 symptom(Patient, leg_weakness, 0.07),
	 symptom(Patient, focal_weakness, 0.05),
	 symptom(Patient, muscle_stiffness_or_tightness, 0.05),
	 symptom(Patient, difficulty_speaking, 0.04),
	 symptom(Patient, leg_stiffness_or_tightness, 0.03),
	 symptom(Patient, fears_and_phobias, 0.03).

disease(Patient, meningitis):-
	 symptom(Patient, headache, 0.87),
	 symptom(Patient, fever, 0.8),
	 symptom(Patient, vomiting, 0.53),
	 symptom(Patient, neck_pain, 0.48),
	 symptom(Patient, ache_all_over, 0.42),
	 symptom(Patient, back_pain, 0.31),
	 symptom(Patient, seizures, 0.31),
	 symptom(Patient, nausea, 0.31),
	 symptom(Patient, low_back_pain, 0.26),
	 symptom(Patient, cough, 0.26),
	 symptom(Patient, leg_pain, 0.26),
	 symptom(Patient, neck_stiffness_or_tightness, 0.21).


disease(Patient, multiple_sclerosis):-
	 symptom(Patient, fatigue, 0.43),
	 symptom(Patient, loss_of_sensation, 0.42),
	 symptom(Patient, headache, 0.31),
	 symptom(Patient, dizziness, 0.28),
	 symptom(Patient, problems_with_movement, 0.27),
	 symptom(Patient, weakness, 0.22),
	 symptom(Patient, leg_weakness, 0.19),
	 symptom(Patient, paresthesia, 0.16),
	 symptom(Patient, disturbance_of_memory, 0.13),
	 symptom(Patient, abnormal_involuntary_movements, 0.12),
	 symptom(Patient, leg_stiffness_or_tightness, 0.12),
	 symptom(Patient, focal_weakness, 0.11).


disease(Patient, migraine):-
	 symptom(Patient, headache, 0.9),
	 symptom(Patient, nausea, 0.46),
	 symptom(Patient, vomiting, 0.39),
	 symptom(Patient, dizziness, 0.26),
	 symptom(Patient, diminished_vision, 0.17),
	 symptom(Patient, disturbance_of_memory, 0.07),
	 symptom(Patient, spots_or_clouds_in_vision, 0.04),
	 symptom(Patient, symptoms_of_the_face, 0.03),
	 symptom(Patient, blindness, 0.02),
	 symptom(Patient, long_menstrual_periods, 0.02).


disease(Patient, dementia):-
	 symptom(Patient, disturbance_of_memory, 0.53),
	 symptom(Patient, problems_with_movement, 0.45),
	 symptom(Patient, dizziness, 0.32),
	 symptom(Patient, depressive_or_psychotic_symptoms, 0.32),
	 symptom(Patient, abnormal_involuntary_movements, 0.26),
	 symptom(Patient, paresthesia, 0.21),
	 symptom(Patient, hostile_behavior, 0.21),
	 symptom(Patient, delusions_or_hallucinations, 0.15),
	 symptom(Patient, difficulty_speaking, 0.13),
	 symptom(Patient, insomnia, 0.13),
	 symptom(Patient, disturbance_of_smell_or_taste, 0.13),
	 symptom(Patient, focal_weakness, 0.09).



disease(Patient, alzheimer_disease):-
	 symptom(Patient, disturbance_of_memory, 0.73),
	 symptom(Patient, depressive_or_psychotic_symptoms, 0.38),
	 symptom(Patient, depression, 0.32),
	 symptom(Patient, problems_with_movement, 0.15),
	 symptom(Patient, hostile_behavior, 0.15),
	 symptom(Patient, delusions_or_hallucinations, 0.1),
	 symptom(Patient, difficulty_speaking, 0.09),
	 symptom(Patient, restlessness, 0.04),
	 symptom(Patient, irregular_appearing_scalp, 0.04),
	 symptom(Patient, fears_and_phobias, 0.02),
	 symptom(Patient, muscle_cramps_contractures_or_spasms, 0.02),
	 symptom(Patient, eye_burns_or_stings, 0.02).


disease(Patient, epilepsy):-
	 symptom(Patient, seizures, 0.91),
	 symptom(Patient, headache, 0.32),
	 symptom(Patient, abnormal_involuntary_movements, 0.13),
	 symptom(Patient, disturbance_of_memory, 0.09),
	 symptom(Patient, difficulty_speaking, 0.04),
	 symptom(Patient, lack_of_growth, 0.02),
	 symptom(Patient, stiffness_all_over, 0.02),
	 symptom(Patient, eye_moves_abnormally, 0.02),
	 symptom(Patient, muscle_weakness, 0.01).


disease(Patient, tension_headache):-
	 symptom(Patient, headache, 0.97),
	 symptom(Patient, neck_pain, 0.51),
	 symptom(Patient, nausea, 0.36),
	 symptom(Patient, dizziness, 0.32),
	 symptom(Patient, anxiety_and_nervousness, 0.28),
	 symptom(Patient, chills, 0.11),
	 symptom(Patient, neck_stiffness_or_tightness, 0.05),
	 symptom(Patient, painful_menstruation, 0.05),
	 symptom(Patient, painful_sinuses, 0.05),
	 symptom(Patient, symptoms_of_the_face, 0.05),
	 symptom(Patient, bleeding_from_ear, 0.02),
	 symptom(Patient, back_cramps_or_spasms, 0.02).

disease(Patient, amyotrophic_lateral_sclerosis_als):-
	 symptom(Patient, weakness, 0.58),
	 symptom(Patient, leg_weakness, 0.42),
	 symptom(Patient, difficulty_speaking, 0.42),
	 symptom(Patient, problems_with_movement, 0.37),
	 symptom(Patient, abnormal_involuntary_movements, 0.37),
	 symptom(Patient, back_pain, 0.37),
	 symptom(Patient, difficulty_in_swallowing, 0.3),
	 symptom(Patient, hand_or_finger_weakness, 0.3),
	 symptom(Patient, leg_pain, 0.3),
	 symptom(Patient, muscle_weakness, 0.22),
	 symptom(Patient, fatigue, 0.22),
	 symptom(Patient, leg_cramps_or_spasms, 0.22).







