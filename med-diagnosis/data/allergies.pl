allergy('nsaid', 'diclofenac_topical_product').
allergy('nsaid', 'piroxicam').

allergy('penicillin', 'ampicillin').

allergy('antibiotic', 'cefuroxime').
allergy('antibiotic', 'cefotaxime').
allergy('antibiotic', 'vancomycin').

allergy('antiepileptic_drug', 'levetiracetam_keppra').
allergy('antiepileptic_drug', 'phenytoin_dilantin').
allergy('antiepileptic_drug', 'lamotrigine_lamictal').
allergy('antiepileptic_drug', 'divalproex_sodium_depakote').
allergy('antiepileptic_drug', 'carbamazepine').
allergy('antiepileptic_drug', 'topiramate_topamax').
allergy('antiepileptic_drug', 'oxcarbazepine_trileptal').
allergy('antiepileptic_drug', 'phenobarbital').
allergy('antiepileptic_drug', 'zonisamide').
allergy('antiepileptic_drug', 'valproic_acid').
allergy('antiepileptic_drug', 'fosphenytoin').
allergy('antiepileptic_drug', 'ethosuximide').
allergy('antiepileptic_drug', 'lacosamide_vimpat').

allergy('sulfonamides', 'celecoxib_celebrex').
allergy('sulfonamides', 'sumatriptan_imitrex').

suggested_treatment(Disease, Allergies, Medication) :- reccommended_medication_therapies(Disease, Medication), allergy(Allergy_name, Medication), \+member(Allergy_name,Allergies).
suggested_treatment(Disease, Allergies, Medication) :- reccommended_medication_therapies(Disease, Medication), \+allergy(Allergy_name, Medication).