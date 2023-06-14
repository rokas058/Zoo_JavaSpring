# Zoo_JavaSpring
# Java Spring Programa

Programa palengvinanti gyvūnų perkėlimą į narvus. CRUD operacijos animals ir enclosure entities. 
POST metodas http://localhost:8080/api/zoo siuncian JSON formatu animals ID ,
automatiskai sugrupuoja gyvunus i laisvus aptvarus
( surusiuoja meseedzius su zoleedziai, zoleedziams randa didziausia aptvara ir juos visus ten apgyvendina,
meseedzius padalina po viena rusi i viena aptvara, jei rusis ta pati , tai programa ju neatskiria ).

Galima gyvunu apgyvendinima pakeisti savarankiskai http://localhost:8080/api/zoo/{animalId}?enclosureId={enclosureId}
