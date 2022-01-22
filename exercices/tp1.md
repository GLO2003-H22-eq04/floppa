# TP1
## Convention de commits

### Comment nommer les commits selon leur type/section/grosseur/etc.

Il faut faire une description de la tâche effectuée dans le message.

### Quoi et quand commiter?

Essayer de séparer les différentes fonctionnalités effectuées dans différents commits. Donc, essayer de faire des petits commit souvent.


### Quelles sont les branches de base (qui sont communes et qui existeront toujours) et quels sont leurs rôles (chacune)?

- ```main``` : branche pour les remises
- ```dev``` : branche pour les fonctionnalités approuvées. La branche par défaut.


### Quelle branche est LA branche principale (contenant le code officiellement intégré et pouvant être remis)?

La branche main.

### Quand créer une nouvelle branche?

Lorsqu'on veut compléter une fonctionnalité.

### Quand faire une demande de changement / d'intégration (pull request / merge request) et sur quelle branche la faire?

Quand le *unit of work* est complété. Fusionner les modifications dans ``` dev ```