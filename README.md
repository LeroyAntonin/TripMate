# TripMate

TripMate est une application Android de planification de voyages. Elle permet de consulter des destinations, d'ajouter des commentaires et de gérer un journal de voyage.

## Fonctionnalités principales

### Destinations

- Affichage des destinations avec image, hôtel, adresse, prix et dates.
- Recherche par ville ou par pays.
- Filtrage par tags.
- Classement des résultats selon les préférences sélectionnées dans le profil.
- Affichage des commentaires dans des cartes arrondies.
- Ajout d'un commentaire directement depuis la page de détail.
- Enregistrement des commentaires dans `db.json`.

### Journal de voyage

- Affichage des journaux avec image, ville, pays, dates et avis.
- Recherche par ville ou par pays.
- Ajout d'un journal.
- Modification d'un journal existant.
- Affichage détaillé d'un journal avec avis présenté dans une carte.
- Suppression d'un journal avec confirmation.
- Saisie et affichage des dates au format `dd/mm/yy` ou `dd/mm/yyyy`.
- Persistance des ajouts, modifications et suppressions via l'API.

### Profil et préférences

- Affichage des tags de destination regroupés par catégories.
- Sélection de plusieurs préférences depuis l'écran Profil.
- Rechargement des catégories en cas d'erreur réseau.

### Navigation

- Navigation entre l'accueil, les journaux et le profil avec une barre de navigation inférieure.
- Retour depuis les pages de détail avec la flèche de la TopBar.
- Clic sur le titre `TripMate` pour revenir à l'accueil.
- Gestion des chargements et des erreurs réseau.

## Technologies utilisées

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- Ktor Client
- Coil pour le chargement des images
- `json-server` pour l'API locale de développement

## Prérequis

- Android Studio
- JDK 11 ou version compatible avec Android Studio
- Node.js et npm
- Un émulateur Android ou un appareil physique

## Installation

1. Cloner le projet :

   ```bash
   git clone <url-du-depot>
   cd TripMate
   ```

2. Ouvrir le projet dans Android Studio.

3. Installer les dépendances de l'API :

   ```bash
   npm install
   ```

## Lancement de l'API

L'application utilise par défaut l'API déployée à l'adresse :

```text
https://tripmate-kifo.onrender.com
```

Pour lancer une instance locale depuis la racine du projet, exécuter :

```bash
npm run api
```

Cette commande démarre `json-server` sur le port `3000` et utilise le fichier :

```text
app/src/main/java/com/example/tripmate/db.json
```

L'API expose notamment les ressources suivantes :

- `GET /destinations`
- `PATCH /destinations/:id`
- `GET /categories`
- `GET /journaux`
- `POST /journaux`
- `PATCH /journaux/:id`
- `DELETE /journaux/:id`

## Configuration réseau

L'adresse de l'API est définie dans :

```text
app/src/main/java/com/example/tripmate/api/DatabaseApi.kt
```

La constante `BASE_URL` pointe par défaut vers l'API Render. Pour utiliser `json-server` en local, la remplacer par l'adresse de la machine qui l'exécute.

- Avec un émulateur Android, utiliser généralement `10.0.2.2:3000` pour accéder à l'ordinateur hôte.
- Avec un appareil physique, utiliser l'adresse IP locale de l'ordinateur.
- L'ordinateur et l'appareil doivent être sur le même réseau.

## Lancement de l'application

1. Utiliser l'API Render par défaut, ou démarrer `json-server` et adapter `BASE_URL` pour une API locale.
2. Sélectionner un émulateur ou un appareil dans Android Studio.
3. Lancer la configuration Android avec **Run**.

L'application peut aussi être compilée depuis la racine du projet :

```bash
.\gradlew.bat :app:assembleDebug
```

Pour vérifier la compilation Kotlin :

```bash
.\gradlew.bat :app:compileDebugKotlin
```
