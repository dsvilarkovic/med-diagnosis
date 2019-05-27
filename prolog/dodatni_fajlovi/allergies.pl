/* https://acaai.org/allergies/types/drug-allergies

Common Triggers of Drug Allergies
    Penicillin and related antibiotics
    Antibiotics containing sulfonamides (sulfa drugs)
    Anticonvulsants (also commonly known as antiepileptic drugs)
    Aspirin, ibuprofen and other nonsteroidal anti-inflammatory drugs (NSAIDs)
    Chemotherapy drugs
*/

allergy(nsaid, 'diclofenac_topical_product').
allergy(nsaid, 'piroxicam').

allergy(penicillin, 'ampicillin').

allergy(antibiotic, 'cefuroxime').
allergy(antibiotic, 'cefotaxime').
allergy(antibiotic, 'ceftriaxone').
allergy(antibiotic, 'vancomycin').

allergy(antiepileptic_drug, 'carbamazepine').
allergy(antiepileptic_drug, 'ethosuximide').
allergy(antiepileptic_drug, 'phenobarbital').
allergy(antiepileptic_drug, 'valproic_acid').
allergy(antiepileptic_drug, 'zonisamide').

allergy(sulfonamides, 'celecoxib_(celebrex)').
allergy(sulfonamides, 'sumatriptan_(imitrex)').


suggested_treatment(Disease, Allergies, Medication) :- recommended_medication(Disease, Medication), allergies(Allergy_name, Medication), \+member(Allergy_name,Allergies).
suggested_treatment(Disease, Allergies, Medication) :- recommended_medication(Disease, Medication), \+allergies(Allergy_name, Medication). 