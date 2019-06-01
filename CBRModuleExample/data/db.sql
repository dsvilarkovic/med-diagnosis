create database stanovi;
use stanovi;
drop table stanovi;
create table stanovi(id INTEGER, godina INTEGER, starost INTEGER, udaljenostGradskogPrevoza INTEGER, brojProdavnica INTEGER, cenaPoKvadratu INTEGER);
insert into stanovi values(1,2012,32,84,10,997);
insert into stanovi values(2,2012,19,306,9,1110);
insert into stanovi values(3,2013,13,561,5,1244);
insert into stanovi values(4,2013,13,561,5,1442);