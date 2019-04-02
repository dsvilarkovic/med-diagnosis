Pokretati scraper sa "python scraping.py ime_ekstenzije_sa_symcat" 
Primer: python scraping.py parkinson-disease (na osnovu http://www.symcat.com/conditions/parkinson-disease)

Ako se ne navede ime ekstenzije, onda default skida sa parkinsona, jer je to difoltno postavljeno

Da se izbegne request-ovanje sa sajta, dodati "-s" 
Primer: python scraping.py parkinson-disease -s (na osnovu http://www.symcat.com/conditions/parkinson-disease)