# TP3

### Rétrospective - Intégration continue et tests

#### Pipeline CI
1. Combien de temps passiez-vous à vérifier et tester manuellement le code lors des intégrations et des remises avant l'implantation du pipeline de tests automatisés?

> Avant le pipeline, il fallait tout tester à la main, ce qui pouvait être assez long et répétitif à faire. Dans un projet scolaire, le temps à allouer n’est pas tant pire, mais dans un projet de plus grande envergure, le temps dépasserait sûrement la limite du possible. Ainsi, dans notre projet, on passait généralement 1 heure avant une PR et plus ou moins 2 heures avant une remise. C’est pourquoi même si les tests semblent être une perte de temps au départ, ils nous font sauver du temps en bout de ligne. 

2. Combien de temps passiez-vous à faire ces vérifications après l'implantation du CI?

> Après le pipeline, les vérifications ne prennent pas beaucoup de temps, car elle se font automatiquement avec nos tests. On peut toujours faire des vérifications manuelles si on le désire, mais ce n’est plus obligatoire. Au niveau du temps, on ne peut pas vraiment le calculer, car cela dépend vraiment à la vitesse à laquelle le pipeline va s’exécuter et si on fait des vérifications à la mains en plus.

3. Quels sont les points positifs que le CI a apporté à votre processus? Donnez-en au moins 3.

> Point 1 : Permet de lancer les tests : En roulant les tests automatiquement, advenant le cas où on oublierait de les exécuter avant une PR, ils vont se lancer tout seul sans avoir besoin de faire quoi que se soit.

> Point 2 : Permet de vérifier le checkstyle : Le pipeline vérifie aussi le checkstyle avant de déterminer si un PR peut être merger ou non.

> Point 3 : Agit comme un filet de sécurité : Si quelque chose ne va pas, la PR ne pourra pas être merge avant que le problème soit réglé, et donc, on n’introduit pas de problème inutilement dans l’application.

> Point 4 : Permet de sauver du temps : Puisque tout est fait automatiquement, il suffit d’attendre que le pipeline soit passée et le tout est joué.


4. Le pipeline CI amène-t-il un élément qui pourrait devenir négatif ou dangeureux pour le processus, le produit et/ou l'équipe? Justifiez.

> Le pipeline pourrait être dangereux s’il est mal configuré ou utilise les mauvais outils. Dans ce cas, il serait plutôt un handicap que d’autre chose dans un projet de développement.  Ainsi, l’équipe n’aurait pas une bonne relation avec celui-ci, et donc, il pourrait être mis de côté pour de mauvaises raisons Aussi, il pourrait apporter un faux sentiment de sécurité, car même si tous les tests semblent passer, il ne faut surtout pas assumer que tous fonctionnent pour autant. C'est pourquoi il est important de s'assurer que le nouveau code soit couvert par des tests.

#### Tests 
1. Quel proportion de temps passez-vous à faire l'implémentation du code fonctionnel versus celui des tests? Est-ce que cette proportion évolue au fil du temps? Pourquoi?

> Au début du TP2, lorsqu’il n’y avait presque aucun test, la proportion de temps était d’environ 70% pour l’implémentation du code et 30% pour les tests. Par exemple, si une tâche prenait 8 heures à faire, alors le code prenait environ 5 heures à être implémenté et les tests prenaient plus ou moins 3 heures à faire. Au fil de l’avancement du TP, cette proportion a changé, car on pouvait réutiliser certain de nos tests en les modifiant un peu. Ainsi, vers la fin du TP2, la proportion était plutôt de 85% pour le code et 15% pour les tests, une amélioration quand même assez importante. En effet, les tests prenaient généralement 2 fois moins de temps à faire, car on pouvait partiellement se baser sur le travail déjà réalisé par les autres membres de l’équipe. 

2. L'implémentation de tests augmente naturellement la charge de travail. Comment cela a-t-il affecté votre processus? (ex : taille des issues/PRs, temps d'implémentation, planification, etc.)

> Notre charge de travail a augmenté, mais pour une bonne raison. En effet, il a fallu modifier notre processus afin de l’adapter à ce nouveau changement. Pour ce faire, nous avons conservé la même dynamique pour nos issues (taille raisonnable, souvent 1 ou 2 par feature), mais nous avons ajouté des issues supplémentaires afin de mieux planifier l’écriture de nos tests. Pour les PR, il y a deux manières de faire : on peut décider d’en faire une pour la feature et une pour les tests, ou encore, d’en faire une seule. En effet, le tout dépend surtout du fait que les tests peuvent ne pas être prêt au moment désiré ou encore, quelqu’un dépend de notre code pour commencer sa partie. C’est pourquoi il vaut mieux parfois  merge notre feature et faire les tests à part pour permettre aux autres de commencer. 

3. Avez-vous plus confiance en votre code maintenant que vous avez des tests? Justifiez.

> Les tests présents dans notre projet permettent de s’assurer que chaque fonctionnalité effectue le travail demandé. Ainsi, on peut affirmer que le niveau de confiance est beaucoup plus élevé qu’auparavant, car on peut facilement vérifier que tout fonctionne encore. En effet, lorsqu’on procède à l’implémentation d’une nouvelle feature, il est important de s’assurer qu’il n’y a pas de régression avec le code déjà existant. Avec les tests, il suffit des les lancer et si tout est vert, il y a des bonnes chances que nous avons correctement effectué notre travail. De plus, lorsqu’on ouvre une PR, les tests sont lancés automatiquement par le pipeline, et donc, on s’assure encore plus qu’on apporte une nouvelle feature et non une tonne de problèmes. 

4. Que pouvez-vous faire pour améliorer l'état actuel (début TP2) de vos tests? Donnez au moins 3 solutions.

> Solution 1 : Standardiser la nomenclature des tests au sein de l’équipe : 
Il faut s’assurer que tous le monde utilise la même manière de nommer les tests afin que le tout soit plus uniforme et facile à comprendre, autant pour les membres de l’équipe que les correcteurs. 

> Solution 2 : Utiliser le code coverage pour avoir un aperçu de la suffisance des tests :
On peut l’utiliser pour vérifier globalement (en pourcentage) à quel point notre projet est bien couvert par des tests. Cependant, il ne faut pas juste se fier à cela pour conclure que tout est testé.

> Solution 3 : Tester tous les cas possibles, même les moins probables et impossible :
Il est facile d’oublier certain cas, et donc, il faut prendre le temps d’y réfléchir et sortir tous les cas afin d’éviter des futurs bugs potentiels provoqués par un comportement inattendu.
### Captures d'écran

##### Projet
<img width="954" alt="projet" src="https://user-images.githubusercontent.com/89783105/159735692-56ff61cf-a865-4e5e-9cc2-62e32615cb71.PNG">

##### Milestone
<img width="747" alt="milestone" src="https://user-images.githubusercontent.com/89783105/159736505-9e539e94-0760-4c73-9936-27c2664ea450.PNG">

##### Issue 1
<img width="713" alt="issue1" src="https://user-images.githubusercontent.com/89783105/159739803-cf84a92c-38fd-4fad-94e4-9226e7076a07.PNG">

##### Issue 2
<img width="707" alt="issue2" src="https://user-images.githubusercontent.com/89783105/159739499-d7fdc815-9b73-4478-8eac-6ce94f49fc08.PNG">

##### Issue 3
<img width="706" alt="issue3" src="https://user-images.githubusercontent.com/89783105/159739507-2e2b5f15-d0cc-4104-af2e-4c66bcce2273.PNG">


##### PR 1

<img width="707" alt="pr1 1" src="https://user-images.githubusercontent.com/89783105/159740622-8909c0b9-c2a8-4052-a9cb-4be918a6a263.PNG">

<img width="555" alt="pr1 3" src="https://user-images.githubusercontent.com/89783105/159733992-5449a1f5-f45b-49c4-aefb-f875332f6835.PNG">

##### PR 2

<img width="714" alt="pr2 1" src="https://user-images.githubusercontent.com/89783105/160739275-61321b3a-5256-4ba0-8faf-e13559f14cf7.PNG">

<img width="519" alt="pr2 2" src="https://user-images.githubusercontent.com/89783105/160739328-6619a0f7-39f6-416b-a116-a46c82c9ea96.PNG">

##### PR 3
<img width="705" alt="pr3 1PNG" src="https://user-images.githubusercontent.com/89783105/161293820-c5a9488d-be77-4bf7-98b3-6336819b39cf.PNG">

<img width="518" alt="pr3 2" src="https://user-images.githubusercontent.com/89783105/161293875-91da817d-eb5c-4160-b2f5-27d929523bb8.PNG">

##### Arbre de commits et de branches
<img width="534" alt="arbre" src="https://user-images.githubusercontent.com/89783105/159765852-c4bc95f0-e9f4-4329-b713-772d4f6f6790.PNG">