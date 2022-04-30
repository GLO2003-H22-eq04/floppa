# TP4

## Open Source Execution

>2 - Comme licence, nous avons choisi "Boost Software License", parce que le projet étant un travail scolaire
> les compagnies, les potentiels contributeurs et les utilisateurs aurons un accès libre au code source et pourrons modifier
> le code librement. 
> De plus, bien que cette licence est assez libre d'utilisation, les utilisateurs devrons garder une copie de la licence 
> utilisée, alors nous sommes assurés que l'application reste open source.
> Finalement, nous informons les potentiels utilisateurs que le code fourni n'est aucunement garantie.

## Questions open source
### Avantages :
Évite de coder plusieurs fois le même code pour résoudre un même problème.

Permet de recevoir le soutien de la communauté pour le projet.

Sécurise l’application, car le open source permet d’exposer plus facilement des failles.
### Défis:
Les gestionnaires du dépôt ont plus de travail, car ils doivent réviser avec grande attention le code qui est proposé parce qu'il provient d’inconnus.

Des efforts supplémentaires doivent être utilisés pour s’assurer que la vision du projet reste la même. Par exemple, il faut activement communiquer avec la communauté pour s’assurer d’être sur la même page sur la direction du projet.

Il est possible que des acteurs malveillants veulent intentionnellement introduire du code vulnérable. Il y a donc un défi de vérifier attentivement le code reçu par plusieurs personnes pour assurer sa sécurité.

### Information surprenante
Le côté légal est pas mal plus développé qu'attendu. Par exemple, il y a beaucoup de types de licences et de problèmes judiciaires liés au changement de cette licence.

## Outils metriques
Tous les outils sont exécuté lorsqu'une pull-request est crée et mise à jour.
### Sécurité

Nous avous choisi l'outil CodeQL qui permet de faire des rapports sur les vulnérabilités dans les librairies utilisés.

![Exécution de la pipeline](img/security-run.png)
![Rapport de sécurité](img/security.png)

### Qualité de code
Nous avous utilisé un nouvel outil de vérification de style de code et enforcé que le code utilise le style forni dans ```custom-style.xml```.

![Exécution de la pipeline](img/checkstyle.png)
![Échec de la pipeline](img/checkstyle-fail.png)

### Couverture de tests
Lorsque la pipeline de tests est exécutée, un rapport de couverture de test est publié avec les artifacts de la pipeline.

![Rapport de couverture](img/test-coverage-md.png)
![Rapport dans la pr](img/test-coverage-pr.png)


## Retrospective

1. La première problématique est que l’équipe avait de la difficulté à estimer le temps restant pour effectuer les différentes tâches. La solution serait d’introduire une étape de poker planning lors de la division des tâches. Cela permettrait donc d’avoir la vision de l’ensemble de l’équipe sur la durée estimée d’une tâche et donnerait donc des estimations plus véridiques. La deuxième problématique est que notre processus se basait sur des rencontres d’équipes hebdomadaires pour communiquer le progrès et s’entraider. Le problème est que nos horaires étaient hautement variables et par conséquent, souvent non-compatibles. La solution serait de retirer ces rencontres hebdomadaires en faveur de petites rencontres courtes qui ne sont pas obligées de comprendre tout le monde de l’équipe. Cela permettrait de s’entraider malgré nos horaires chaotiques.

2. Pour intégrer un nouvel outil technologique, on confiait la tâche à un membre de l’équipe d’aller apprendre ce nouvel outil. Lorsqu’il était devenu familier avec l’outil, il faisait donc un rapport à l’équipe pour les former à l’utilisation de cet outil. 

    Nous n’avons pas vraiment rencontré de bogues lors de l’intégration de nouveaux outils, car ils étaient bien recherchés.

    Nous explorions les nouvelles fonctionnalités à l’aide de tests manuels et unitaires. Les tests manuels étaient plus utilisés initialement pour comprendre le fonctionnement de la technologie utilisée. Dès que le fonctionnement général était compris, nous nous replions sur des tests unitaires pour accélérer le développement.

    Faire des tests automatisés est une excellente façon d’accélérer le développement comme ils sont beaucoup plus rapides que les tests manuels.
    Toutes les features demandées à chaque remise ont été complétées intégralement.

3. Notre équipe à atteint un niveau d’autonomie individuelle assez élevé rapidement.

    La division des tâches était équitable pour toutes les itérations.

4. Le conseil qu’on donnerait est de se prendre tôt pour effectuer le travail, car cela permet de faire un travail de plus haute qualité.

5. Le concept de DevOps était nouveau pour nous, plus particulièrement le déploiement automatisé. Nous avons pu voir les bénéfices que cette approche apporte au projet comme minimiser le temps perdu à déployer manuellement la dernière version de l’application.
Le concept de code review formel était aussi nouveau pour nous. Nous avions l’habitude de ne pas faire de pull request. Ce processus de révision de code a amélioré la qualité du code.


### Captures d'écran

##### Projet

<img width="1000" alt="projet" src="https://user-images.githubusercontent.com/90847140/165986660-d1efcc86-c8b4-4cea-82ba-9820143a6e71.png">

##### Milestone

<img width="1000" alt="milestone" src="https://user-images.githubusercontent.com/90847140/165986852-5894477c-c237-4a78-8d21-ac6ee7289ec6.png">

##### Issue 1

<img width="1000" alt="issue1" src="https://user-images.githubusercontent.com/90847140/165987576-417d6bb4-f122-48d6-b55b-9ef14008acd0.png">

##### Issue 2

<img width="1000" alt="issue2" src="https://user-images.githubusercontent.com/90847140/165988037-3399d540-a880-453c-806e-aa97dbe62ffa.png">

##### Issue 3

<img width="1000" alt="issue3" src="https://user-images.githubusercontent.com/90847140/165988193-e1404ee2-7370-4e73-8c54-d09bcc4795f1.png">

##### PR 1

<img width="840" alt="pr2 1" src="https://user-images.githubusercontent.com/89783105/166085571-ead16585-8a08-4ce9-81a4-3c22cfed65a7.PNG">

<img width="836" alt="pr2 2" src="https://user-images.githubusercontent.com/89783105/166085581-baa52ae9-233b-4fd9-bf59-8462bfee4910.PNG">

##### PR 2

<img width="855" alt="pr 3 1" src="https://user-images.githubusercontent.com/89783105/166085656-2668ec3c-a9ec-45d9-82c1-c37374a1ef6c.PNG">

<img width="856" alt="pr 3 2" src="https://user-images.githubusercontent.com/89783105/166085596-df1593a4-1158-472f-927c-5f60340d5810.PNG">

##### PR 3

<img width="845" alt="pr 1 1" src="https://user-images.githubusercontent.com/89783105/166085757-a72dfca2-0412-4d7f-a041-3afd7e46dc8e.PNG">

<img width="860" alt="pr1 2" src="https://user-images.githubusercontent.com/89783105/166085776-6583e41a-343f-4440-8f3f-3fe5f417c443.PNG">

##### Arbre de commits et de branches

<img width="1000" alt="arbre" src="https://user-images.githubusercontent.com/90847140/165989557-37ca0a55-dfe5-4efb-88fa-814c01b2fbfb.png">
