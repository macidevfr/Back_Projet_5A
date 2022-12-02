# Back_Projet_5A

DEPLOIEMENT :

Tout d'abord j'ai créé un service de base de données PostgreSql
J'ai ensuite inséré les crédentials fournis dans mon fichier application.yml, afin que mon application puisse se connecter à la bdd.

Concernant l'application Spring, j'ai créé un dockerfile permettant d'effectuer un gradle build (sans les tests), puis de récupérer le jar généré


Mogenius, lorsque j'ai créé mon service, a pu automatiquement récupérer les fichiers à déployer ainsi que le dockerfile


Adresse BDD : db-project-9gcdfz:5432
Adresse Backend : https://backend-prod-maelcipriani-mogenius-abppe7.mo1.mogenius.io/

------------------------------------------------------------------------------------------

git checkout -b nombranche : permet de créer et de se rendre sur une nouvelle branche

git checkout nombranche : permet de se rendre sur une branche existante

git pull : récupère le code sur le repo 

git add . : ajoute le code dans la mémoire git

git commit -m "message" : commit le code 

git push : envoie le code sur le repo 

------------------------------------------------------------------------------------------
Faire git pull dès que le projet est modifié

Pour upload du code, faire les manip suivantes :

git add . -> git commit -m "message" -> git push
