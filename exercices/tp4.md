# TP4

## Open Source Execution

>2 - Comme licence, nous avons choisi "Boost Software License", parce que le projet étant un travail scolaire
> les compagnies, les potentiels contributeurs et les utilisateurs aurons un accès libre au code source et pourrons modifier
> le code librement. 
> De plus, bien que cette licence est assez libre d'utilisation, les utilisateurs devrons garder une copie de la licence 
> utilisée, alors nous sommes assurés que l'application reste open source.
> Finalement, nous informons les potentiels utilisateurs que le code fourni n'est aucunement garantie.

## Outils metriques
Tous les outils sont exécuté lorsque une pull-request est crée et mise à jour.
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


