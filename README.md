# Mon Nuancier — première version Android
Application personnelle hors connexion, sans compte ni permission Internet.

## Utilisation
La version Mon-Nuancier.html permet de tester l’interface dans un navigateur moderne. Les données du navigateur et de l’application Android sont séparées. Utiliser Sauvegarde pour les transférer.
330 pastilles commerciales approximatives sont intégrées : 168 Gel, 72 Shiny, 90 Acrylic. Les identifiants Gel suivent les rangées du nuancier fourni. Les codes Shiny et Acrylic sont ceux visibles sur les images.
Les livrets et leurs légendes sont ajoutés manuellement. Les suggestions utilisent une distance RVB simple, sans garantie de correspondance physique. Pas de scan automatique ni de catalogue de légendes intégré.

## Compilation Android
Ouvrir ce dossier dans Android Studio, installer le SDK 35 et compiler avec JDK 17 et Gradle 8.9. Le projet ne contient pas de wrapper Gradle.
Autre possibilité : placer le contenu de ce dossier dans un dépôt GitHub et lancer le workflow Compiler Android. Télécharger l’artefact Mon-Nuancier-APK et installer app-debug.apk sur Android 8 ou plus récent. La compilation n’a pas été exécutée dans l’environnement de création, dépourvu de SDK Android et de Gradle. Le workflow nécessite Internet.
L’APK debug est une version de test ; sauvegarder les données avant désinstallation. Garder la même clé de signature pour les futures mises à jour.

## Données
Stockage local via localStorage dans le WebView. Copier régulièrement la sauvegarde JSON depuis l’application et la garder dans un fichier. Les pastilles affichées varient selon l’écran. Aucune valeur officielle d’encre ni légende Hachette n’est incluse.

## Version 0.2 — références commerciales
330 pastilles approximatives extraites des trois images Amazon fournies : 168 Gel (rangée et position), 72 Shiny (codes SG), 90 Acrylic (codes visibles). Les codes Acrylic et Shiny sont transcrits du nuancier, pas vérifiés sur chaque stylo. Les Gel n’ont aucun code constructeur visible. La migration remplit les emplacements non renseignés et conserve les couleurs déjà saisies et les correspondances. Effet Gel non vérifié : la valeur Uni initiale est modifiable.

## Version 0.3 — Photo couleur
Ouvrir Photo couleur, prendre une photo ou importer une image, puis toucher le centre de la couleur de légende. Les cinq suggestions sont classées par distance CIE76 dans l’espace Lab (sRGB D65), sans mesure de brillance. Correction facultative par prélèvement du blanc du papier (approximation par gains RVB). Possibilité d’associer directement une suggestion à un code de livret et une page. Les images sont analysées localement et ne sont pas exportées dans les sauvegardes. La capture Android utilise l’application appareil photo du téléphone et un fichier temporaire dans le cache privé ; aucune permission caméra dans cette application. La version HTML dépend du navigateur pour proposer la caméra ; importer une photo reste possible. Le parcours caméra Android doit encore être testé sur un téléphone après compilation.
