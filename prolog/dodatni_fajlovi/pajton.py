
file = open("sulf.txt", "r")
content = file.read()

antibiotics_list = content.split('\n')

for i in range(len(antibiotics_list)):
    antibiotics_list[i] = antibiotics_list[i].lower().strip().replace(" ", "_").replace("-","_")


all_med_list = ['riluzole', 'baclofen', 'tizanidine', 'glycopyrrolate', 'celecoxib_(celebrex)', 'vitamin_e_(vita_e)', 'quinine', 'ubiquinone_(q10)', 'fibrin_sealant_topical', 'beta_carotene', 'creatine', 'dantrolene', 'coal_tar_topical', 'topiramate_(topamax)', 'midrin', 'rizatriptan_(maxalt)', 'eletriptan_(relpax)', 'butorphanol_(stadol)', 'diclofenac_topical_product', 'piroxicam', 'monobenzone_topical', 'sumatriptan_(imitrex)', 'topiramate_(topamax)', 'prochlorperazine_(compro)', 'rizatriptan_(maxalt)', 'nalbuphine_(nubain)', 'propranolol', 'eletriptan_(relpax)', 'midrin', 'zolmitriptan_(zomig)', 'nortriptyline', 'butorphanol_(stadol)', 'ropinirole', 'pramipexole_(mirapex)', 'amantadine', 'donepezil_(aricept)', 'rasagiline_(azilect)', 'carbidopa', 'entacapone_(comtan)', 'memantine_(namenda)', 'selegiline', 'trihexyphenidyl_(artane)', 'rivastigmine_(exelon)', 'donepezil_(aricept)', 'memantine_(namenda)', 'rivastigmine_(exelon)', 'galantamine', 'mirtazapine', 'thiamine', 'pramipexole_(mirapex)', 'felodipine', 'bisacodyl_(the_magic_bullet)', 'modafinil_(provigil)', 'raloxifene_(evista)', 'deferasirox_(exjade)', 'interferon_beta_1a_(avonex)', 'glatiramer_(copaxone)', 'baclofen', 'modafinil_(provigil)', 'oxybutynin', 'tizanidine', 'natalizumab_(tysabri)', 'tolterodine_(detrol)', 'amantadine', 'solifenacin_(vesicare)', 'azathioprine', 'cyclophosphamide', 'armodafinil_(nuvigil)', 'donepezil_(aricept)', 'memantine_(namenda)', 'rivastigmine_(exelon)', 'galantamine', 'dapiprazole_ophthalmic', 'alendronate_cholecalciferol', 'bromocriptine', 'fluvastatin_(lescol)', 'citric_acid', 'astemizole', 'hypromellose_(genteal)', 'levetiracetam_(keppra)', 'phenytoin_(dilantin)', 'lamotrigine_(lamictal)', 'divalproex_sodium_(depakote)', 'carbamazepine', 'topiramate_(topamax)', 'oxcarbazepine_(trileptal)', 'phenobarbital', 'zonisamide', 'valproic_acid', 'fosphenytoin', 'ethosuximide', 'lacosamide_(vimpat)', 'ceftriaxone', 'vancomycin', 'ampicillin', 'prochlorperazine_(compro)', 'midazolam_(versed)', 'cefotaxime', 'acyclovir', 'phenobarbital', 'gentamicin_ophthalmic', 'cefuroxime', 'micafungin', 'amphotericin_b', 'tapentadol_(nucynta)']

print(antibiotics_list)

for antibiotic in antibiotics_list:

    if antibiotic in all_med_list:
        print(antibiotic)


file.close()