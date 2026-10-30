# Étape 06 — Utiliser tes destinations dans TripMate

## Ce que tu vas faire

Ton application affiche déjà la barre TripMate et le champ de recherche. Tu as aussi un premier fichier `destinations.json`. Nous allons adapter son contenu à la structure décidée pour tes deux pages : les destinations proposées et les souvenirs ajoutés par l’utilisateur.

Nous allons reprendre **la méthode du cours Netfloux**, avec tes destinations à la place des films :

1. Décrire les informations d'une destination dans une fiche Kotlin.
2. Préparer les fiches qui représentent les données lues dans le JSON.
3. Passer de ces fiches à celles de l'application.
4. Placer ton texte JSON dans une variable Kotlin, comme `movieData` dans le cours.
5. Lire les destinations, puis afficher leurs noms sur ton écran.
6. Décrire une fiche de journal pour préparer les futurs ajouts de souvenirs.

À la fin, tu verras **Bali et Santorin** dans une liste. Taper `bal` laissera Bali. Les hôtels, prix, dates, tags et commentaires seront prêts dans les fiches, même si nous affichons ici seulement le nom et le pays.

Le journal sera **vide au départ**. Plus tard, le bouton « + » permettra à l’utilisateur de saisir un souvenir et de choisir ses photos. Dans cette étape, tu prépares la fiche nécessaire ; tu ne construis pas encore le formulaire ni sa sauvegarde.

Les cartes avec images de ta maquette viendront après. Cette étape sert d'abord à préparer et à lire les données.

## Le lien avec le cours

| Dans Netfloux | Dans ton application |
| --- | --- |
| `Movie` : une fiche de film | `Destination` : une fiche de destination |
| `MovieDto` : le groupe de films lus | `DestinationDto` : le groupe de destinations lues |
| `SingleMovieDto` : un film lu | `SingleDestinationDto` : une destination lue |
| `mapMovie` : le passage à la fiche de l'application | `mapDestination` : le même rôle pour une destination |
| `movieData` : le texte JSON | `destinationData` : ton texte JSON |
| `loadMovies()` : la lecture des films | `loadDestinations()` : la lecture des destinations |

Tu gardes des noms français : `nom`, `pays`, `hotel`, etc. `nom` convient à une ville, une île ou une région. Par exemple, Bali est une île : nous ne l’appelons donc plus `ville` dans les données.

Le prix est affiché **par personne**, comme dans la maquette. Les prix et dates ci-dessous sont des exemples de cours, pas des offres réelles vérifiées. Les noms, hôtels, adresses et commentaires reprennent tes exemples. Les photos seront ajoutées dans une étape suivante.

## Avant de commencer

Travaille dans **TripMate**, pas dans le projet Netfloux. Sauvegarde les fichiers que tu vas modifier si tu veux conserver tes essais.

Tu vas créer cinq fichiers Kotlin. Si tu as déjà commencé le premier guide, garde tes fichiers et adapte les déclarations indiquées : ne crée pas une deuxième classe du même nom. Leur emplacement de départ est :

```text
app/src/main/java/com/example/tripmate/
```

| Fichier à créer à partir de cet emplacement | Son rôle |
| --- | --- |
| `model/Destination.kt` | Décrire une destination utilisée par l'application. |
| `model/Journal.kt` | Décrire un souvenir que l’utilisateur pourra ajouter. |
| `model/DestinationDto.kt` | Décrire les destinations telles qu'on les lit dans le JSON. |
| `mapper/DestinationMapper.kt` | Passer d'une fiche lue à une fiche de l'application. |
| `data/DestinationData.kt` | Garder le texte JSON et le lire. |

Tu modifieras ensuite seulement `MainActivity.kt` pour afficher la liste. Tu gardes ta barre, tes couleurs, ton champ de recherche et ton icône.

Pour créer un dossier Kotlin dans Android Studio, fais un clic droit sur le groupe **`com.example.tripmate`**, puis **New → Package**. Donne le nom court demandé : `model` ou `mapper`. Le groupe `data` existe déjà.

Dans le groupe concerné, choisis **New → Kotlin Class/File**, donne le nom du fichier sans `.kt`, puis choisis **File**. Par exemple, crée `Destination` dans `model`.

Android Studio peut écrire la ligne `package` automatiquement. Si elle est déjà présente et correcte, garde-la : ne la recopie pas une deuxième fois.

Les numéros TM06 ci-dessous servent à te repérer dans ce document. Tu n'as pas besoin d'ajouter des commentaires dans ton code pour suivre le guide.

## TM06-1 — Décrire une destination

**Ouvre le nouveau fichier `model/Destination.kt`.**

La fiche doit pouvoir garder toutes les informations d'une destination. Elle ne dessinera pas encore de carte à l'écran.

En haut du fichier, vérifie cette ligne :

```kotlin
package com.example.tripmate.model
```

`package` indique le groupe auquel appartient le fichier. Ici, `model` est le groupe de nos fiches.

En dessous, écris cette fiche :

```kotlin
data class Destination(
    val id: Int,
    val nom: String,
    val pays: String,
    val hotel: String,
    val adresse: String,
    val prix: Int,
    val devise: String,
    val unitePrix: String,
    val dateArrivee: String,
    val dateDepart: String,
    val imageUrl: String,
    val tags: List<String>,
    val commentaires: List<String>,
)
```

Une **`data class`** regroupe des informations dans une fiche. `Destination` est le nom de cette fiche.

Chaque ligne nomme une information. Après les deux-points `:`, nous indiquons son type, c'est-à-dire ce qu'elle peut contenir :

- `String` : un texte, comme `Bali` ou `Indonésie`.
- `Int` : un nombre entier, comme `1` pour l’identifiant ou `89` pour le prix de l’exemple Bali.
- `List<String>` : plusieurs textes, comme les tags `Plage`, `Nature` et `Bien-être`.

`val` signifie que cette information ne sera pas remplacée dans la fiche après sa création. Les virgules séparent les informations.

`id` est le numéro qui distingue une destination des autres. Chaque destination doit avoir un numéro différent. Ce numéro permettra plus tard de retrouver la bonne fiche au clic.

`nom` est le nom affiché : Bali ou Santorin. `devise` indique la monnaie (`EUR` pour l’euro) et `unitePrix` précise ce que le prix concerne (`personne`).

`dateArrivee` et `dateDepart` sont les dates du séjour. Elles restent des textes sous la forme année-mois-jour, par exemple `2027-11-02`. Ce ne sont pas les dates où l’on a effectué la réservation. La date de départ doit être après la date d’arrivée.

`imageUrl` gardera l’adresse de la photo de la destination. Pour l’instant, sa valeur est un texte vide : aucune image distante ne sera encore affichée.

Le prix reste un nombre entier pour les exemples de cette étape. L’unité est désormais explicite : `89`, `EUR` et `personne` signifient ensemble **89 euros par personne**. Si ton vrai tarif est par nuit, il faudra modifier l’unité et le texte de l’écran ensemble, sans simplement changer leur sens.

## TM06-2 — Décrire la forme lue dans le JSON

**Ouvre le nouveau fichier `model/DestinationDto.kt`.**

Le cours distingue les données reçues et les données utilisées par l'application. Nous gardons cette séparation.

La fiche qui représente les données reçues s'appelle un **DTO**, ou objet de transport des données. Ici, les données viennent de notre texte local ; il n'y a pas encore de serveur.

En haut, vérifie le package :

```kotlin
package com.example.tripmate.model
```

En dessous, ajoute :

```kotlin
data class DestinationDto(val destinations: List<SingleDestinationDto>)
```

Ton JSON contient une liste nommée `destinations`. Cette première fiche représente ce groupe : son champ `destinations` contient une liste de destinations.

Le nom `SingleDestinationDto` peut être rouge pour l'instant : nous allons le définir juste dessous. `Single` signifie ici « une seule destination ».

Après la ligne précédente, ajoute :

```kotlin
data class SingleDestinationDto(
    val id: Int,
    val nom: String,
    val pays: String,
    val hotel: String,
    val adresse: String,
    val prix: Int,
    val devise: String,
    val unitePrix: String,
    val dateArrivee: String,
    val dateDepart: String,
    val imageUrl: String,
    val tags: List<String>,
    val commentaires: List<String>,
)
```

Les noms correspondent exactement à la nouvelle structure JSON que tu prépareras à TM06-4. Par exemple, `hotel` s'écrit sans accent dans le code, comme dans ton fichier.

Dans Netfloux, certaines informations changent de forme : l'année passe d'un texte à un nombre. **Tes informations ont déjà les types souhaités.** Nous n'allons pas transformer artificiellement ton prix en texte ou tes listes en une seule phrase. Nous gardons les deux fiches pour suivre l'organisation du cours.

## TM06-3 — Passer à la fiche de l'application

**Ouvre le nouveau fichier `mapper/DestinationMapper.kt`.**

Nous allons écrire une fonction qui reçoit une `SingleDestinationDto` et renvoie une `Destination`. Une fonction est un morceau de code qui effectue une tâche.

En haut, vérifie le package, puis ajoute les deux imports :

```kotlin
package com.example.tripmate.mapper

import com.example.tripmate.model.Destination
import com.example.tripmate.model.SingleDestinationDto
```

Les lignes `import` permettent d'utiliser ici les fiches écrites dans le groupe `model`.

Après ces imports, écris :

```kotlin
fun mapDestination(dto: SingleDestinationDto): Destination = Destination(
    id = dto.id,
    nom = dto.nom,
    pays = dto.pays,
    hotel = dto.hotel,
    adresse = dto.adresse,
    prix = dto.prix,
    devise = dto.devise,
    unitePrix = dto.unitePrix,
    dateArrivee = dto.dateArrivee,
    dateDepart = dto.dateDepart,
    imageUrl = dto.imageUrl,
    tags = dto.tags,
    commentaires = dto.commentaires,
)
```

Lis la première ligne ainsi :

- `fun` : nous déclarons une fonction.
- `mapDestination` : c'est son nom.
- `dto: SingleDestinationDto` : elle reçoit une fiche lue, que nous appelons `dto`.
- `: Destination` : elle renvoie une fiche pour notre application.
- `= Destination(...)` : voici comment cette fiche est construite.

Chaque ligne recopie une information. Par exemple, `nom = dto.nom` place le nom reçu dans la nouvelle fiche. Le point de `dto.nom` signifie « prends le nom de cette fiche ».

Le groupe `mapper` contient donc le passage d'une forme de données à l'autre. Pour tes données actuelles, ce passage est une copie. Si la forme reçue change plus tard, ce sera l'endroit où l'adapter.

## TM06-4 — Préparer le nouveau JSON et le placer dans Kotlin

Tu vas travailler avec **deux listes**, qui n’ont pas le même rôle :

- `destinations` contient les propositions préparées pour l’accueil.
- `journaux` contient les souvenirs saisis par l’utilisateur. Elle est vide au départ : `[]`.

Le journal ne doit donc pas être rempli avec des voyages inventés pour l’utilisateur. Notre exemple commence sans souvenir enregistré.

### 1. Préparer ton fichier JSON

Ouvre :

```text
app/src/main/java/com/example/tripmate/data/destinations.json
```

**Avant de remplacer son contenu, fais une copie de ton ancien fichier en dehors de `app/src/`**, par exemple dans un dossier personnel `sauvegardes-tripmate`. Tu y garderas les dix destinations et les anciens exemples de journaux si tu veux les reprendre plus tard.

Dans le fichier original `destinations.json`, remplace le contenu par le JSON ci-dessous. Il contient volontairement deux destinations pour apprendre et vérifier la lecture. Tu pourras ensuite ajouter les autres avec les mêmes champs et des identifiants différents.

```json
{
  "destinations": [
    {
      "id": 1,
      "nom": "Bali",
      "pays": "Indonésie",
      "hotel": "Ubud Rice Terrace Resort",
      "adresse": "Jalan Raya Tegalalang, 80561",
      "prix": 89,
      "devise": "EUR",
      "unitePrix": "personne",
      "dateArrivee": "2027-11-02",
      "dateDepart": "2027-11-07",
      "imageUrl": "",
      "tags": ["Plage", "Nature", "Bien-être"],
      "commentaires": [
        "Cadre verdoyant et très reposant.",
        "Les massages proposés au spa sont excellents."
      ]
    },
    {
      "id": 2,
      "nom": "Santorin",
      "pays": "Grèce",
      "hotel": "Caldera View Suites",
      "adresse": "12 Oia Main Street, 84702",
      "prix": 210,
      "devise": "EUR",
      "unitePrix": "personne",
      "dateArrivee": "2027-06-22",
      "dateDepart": "2027-06-27",
      "imageUrl": "",
      "tags": ["Plage", "Romantique", "Paysages"],
      "commentaires": [
        "Le coucher de soleil depuis la terrasse est magnifique.",
        "Suite très calme avec une vue exceptionnelle."
      ]
    }
  ],
  "journaux": []
}
```

Les deux séjours contiennent les mêmes noms de champs. Les prix et dates servent d’exemples ; remplace-les par tes valeurs confirmées lorsque tu auras les vrais tarifs et séjours.

`[]` signifie une liste vide. Il n’y a pas de journal à lire au premier lancement. Le nombre de souvenirs sera calculé à partir de cette liste ; tu n’ajoutes pas un champ séparé pour le compteur.

### 2. Préparer le haut du fichier Kotlin

Ouvre le nouveau fichier `data/DestinationData.kt`. Vérifie le package et ajoute les imports :

```kotlin
package com.example.tripmate.data

import com.example.tripmate.mapper.mapDestination
import com.example.tripmate.model.Destination
import com.example.tripmate.model.DestinationDto
import com.example.tripmate.model.SingleDestinationDto
import org.json.JSONObject
```

`JSONObject` est l’outil Android qui lit un objet JSON. Il est déjà disponible : aucune bibliothèque à ajouter et aucun changement Gradle pour cette lecture.

### 3. Préparer la place pour le texte

Après les imports, écris :

```kotlin
const val destinationData = """

"""
```

`const val` donne un nom à un texte fixe. Les triples guillemets permettent de garder un texte sur plusieurs lignes. La première série ouvre le texte ; la deuxième le ferme.

### 4. Copier ton nouveau JSON dans la constante

Dans `destinations.json`, copie tout le contenu avec Ctrl+A, puis Ctrl+C.

Reviens dans `DestinationData.kt`. Place le curseur sur **la ligne vide entre les deux séries de triples guillemets**, puis colle avec Ctrl+V.

Tu dois avoir, dans cet ordre :

1. Le package et les imports.
2. La ligne `const val destinationData = """`.
3. Tout le JSON, depuis sa première accolade jusqu’à sa dernière, avec les deux listes.
4. La ligne de fermeture `"""`.

Ne colle pas le JSON à la place de tout le fichier Kotlin. Le package et les imports restent au-dessus.

Nous suivons ainsi la méthode de `movieData` dans le cours. **L’application lit destinationData, pas directement le fichier destinations.json.** Le fichier JSON sert ici de support de préparation. Si tu modifies ensuite le catalogue, reporte le changement dans la constante et relance l’application.

Cette constante contient les données de départ. Elle ne sera pas le lieu où l’application écrira les souvenirs ajoutés par l’utilisateur. Nous préparerons leur forme à TM06-7.

Dans le JSON, `{ }` entoure un objet, `[ ]` une liste et `:` relie un nom à sa valeur. Garde les guillemets et les virgules pendant la copie.
## TM06-5 — Lire les destinations et préparer les fiches

**Reste dans `DestinationData.kt`. Tous les ajouts suivants vont après les triples guillemets de fermeture, donc en dehors du texte JSON.**

Nous allons d'abord chercher la liste `destinations`, puis lire chaque destination, puis renvoyer les fiches utilisables par l'application.

**Cette étape est construite en plusieurs morceaux. La fonction sera complète après le point 4. Attends ce moment avant de compiler.**

### 1. Commencer la fonction de lecture

Sous la constante, ajoute :

```kotlin
fun loadDestinations(): List<Destination> {
    val array = JSONObject(destinationData).getJSONArray("destinations")
}
```

`loadDestinations` signifie ici « charger les destinations ». Les parenthèses vides indiquent qu'elle ne reçoit aucune valeur. `List<Destination>` indique qu'elle doit renvoyer une liste de fiches Destination.

`JSONObject(destinationData)` lit le texte. `.getJSONArray("destinations")` prend seulement la liste qui porte ce nom. Le nom doit correspondre exactement à la clé de ton JSON.

### 2. Lire une destination et ses deux listes de textes

Entre les accolades de `loadDestinations`, **après la ligne `val array = ...` et avant l'accolade finale de la fonction**, ajoute :

```kotlin
val entries = List(array.length()) { index ->
    val item = array.getJSONObject(index)

    val tagArray = item.getJSONArray("tags")
    val tags = List(tagArray.length()) { tagIndex ->
        tagArray.getString(tagIndex)
    }

    val commentaireArray = item.getJSONArray("commentaires")
    val commentaires = List(commentaireArray.length()) { commentaireIndex ->
        commentaireArray.getString(commentaireIndex)
    }
}
```

Ce morceau parcourt les destinations :

- `array.length()` donne leur nombre : 2 dans le nouvel exemple.
- `List(...) { index -> ... }` prépare un élément pour chaque position de la liste.
- `index` est cette position : 0 pour la première destination, 1 pour la deuxième, etc.
- `item` contient la destination que nous sommes en train de lire.

Une destination contient aussi deux listes. Le bloc `tags` lit les tags un par un. Le bloc `commentaires` fait la même chose avec les commentaires. `getString(...)` lit un texte à la position demandée.

La flèche `->` sépare la valeur reçue à gauche des instructions à exécuter à droite.

### 3. Rassembler les informations d'une destination

Tu es toujours dans `loadDestinations`. Repère le bloc qui commence par `val entries = List(array.length()) { index ->`.

Après le bloc qui crée `commentaires`, mais **avant l'accolade qui ferme `entries`**, ajoute :

```kotlin
SingleDestinationDto(
    id = item.getInt("id"),
    nom = item.getString("nom"),
    pays = item.getString("pays"),
    hotel = item.getString("hotel"),
    adresse = item.getString("adresse"),
    prix = item.getInt("prix"),
    devise = item.getString("devise"),
    unitePrix = item.getString("unitePrix"),
    dateArrivee = item.getString("dateArrivee"),
    dateDepart = item.getString("dateDepart"),
    imageUrl = item.getString("imageUrl"),
    tags = tags,
    commentaires = commentaires,
)
```

Ce morceau construit une fiche à partir des informations lues : `getString("nom")` lit le nom ; `getInt("prix")` lit le prix comme un nombre entier.

Pour `tags = tags`, le nom à gauche est le champ de la fiche ; à droite, c'est la liste que tu viens de lire. Même principe pour les commentaires.

Comme `SingleDestinationDto(...)` est la dernière expression du bloc `entries`, la fiche construite devient l'élément ajouté à cette liste. Tu obtiens ainsi une fiche par destination.

Pour vérifier l'emplacement : `SingleDestinationDto(...)` est **après** le bloc `commentaires`, pas à l'intérieur de celui-ci.

### 4. Renvoyer les fiches de l'application

Après l'accolade qui ferme `entries`, mais **avant l'accolade finale de `loadDestinations`**, ajoute :

```kotlin
val dto = DestinationDto(destinations = entries)
return dto.destinations.map { entry -> mapDestination(entry) }
```

La première ligne rassemble les fiches lues dans `DestinationDto`.

La deuxième transforme chaque fiche avec la fonction `mapDestination` écrite à TM06-3. `map` veut dire ici « effectue cette transformation pour chaque élément ». `return` donne la liste obtenue au code qui appellera `loadDestinations()`.

Ta fonction est maintenant complète. Fais **Ctrl+Alt+L** pour aligner les lignes. L'application n'affiche pas encore ces données : il faut maintenant les utiliser sur l'écran.

## TM06-6 — Utiliser les destinations sur ton écran actuel

**Ouvre `app/src/main/java/com/example/tripmate/MainActivity.kt`.**

Dans le cours, la liste des films existe déjà à cette étape. Dans ton fichier actuel, il y a le champ de recherche mais pas encore de liste. Nous allons donc **ajouter une liste sous ce champ**.

Tu conserves le reste de ton écran : le Scaffold, la barre TripMate, les marges et ton champ avec sa loupe. Tu ne modifies pas les fichiers du dossier `screens` pour cette étape.

### 1. Ajouter les imports nécessaires

En haut du fichier, après `package` et avec les autres imports, ajoute seulement les lignes qui manquent :

```kotlin
import androidx.compose.runtime.remember
import com.example.tripmate.data.loadDestinations
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
```

Les deux derniers imports sont déjà présents dans le fichier que nous avons lu : ne les ajoute pas en double.

`remember` gardera les fiches pendant l'affichage. `loadDestinations` est la fonction que tu viens d'écrire. `LazyColumn` affiche une liste défilante et `items` décrit une ligne par élément.

Garde aussi `rememberSaveable`, déjà utilisé pour le texte de recherche : ce n'est pas le même outil.

### 2. Charger les fiches

Dans le contenu de ta `Column`, retrouve cette ligne existante :

```kotlin
var query by rememberSaveable { mutableStateOf("") }
```

**Juste en dessous**, ajoute :

```kotlin
val destinations = remember { loadDestinations() }
```

`loadDestinations()` lit ton texte JSON et renvoie les fiches. `remember` garde cette liste pendant que cette partie de l'interface est présente : elle n'est pas relue à chaque lettre tapée.

Ce n'est pas une sauvegarde sur disque. Les données fixes sont dans ta constante `destinationData`.

### 3. Garder seulement les destinations recherchées

Juste sous la ligne précédente et **avant `OutlinedTextField(...)`**, ajoute :

```kotlin
val visibleDestinations = destinations.filter { destination ->
    destination.nom.contains(query, ignoreCase = true)
}
```

`filter` parcourt les destinations et garde celles qui respectent la condition. `destination` est la fiche examinée.

`destination.nom.contains(query, ignoreCase = true)` vérifie si le nom contient le texte tapé. Les majuscules et les minuscules ne font pas de différence. Avec `bal`, Bali reste dans la liste. Avec un champ vide, les deux destinations restent.

`visibleDestinations` contient le résultat à afficher. Le champ de recherche continue à utiliser le même `query` : ne crée pas une deuxième variable pour la recherche.

### 4. Ajouter la zone de liste

Dans la même `Column`, repère la fin de ton champ `OutlinedTextField(...)`. Sa dernière ligne de réglage est actuellement :

```kotlin
modifier = Modifier.fillMaxWidth(),
```

La parenthèse `)` juste après ferme le champ. **Après cette parenthèse, mais avant l'accolade qui ferme `Column`**, ajoute :

```kotlin
LazyColumn(
    modifier = Modifier.weight(1f).fillMaxWidth(),
) {
}
```

`weight(1f)` donne à la liste la hauteur restante sous le champ de recherche. `1f` est l'écriture du nombre 1 dans le type de nombre attendu ici, appelé `Float`. `fillMaxWidth()` prend toute la largeur disponible.

Cette liste doit être directement dans la `Column`, pas dans `OutlinedTextField` ni dans le bloc de l'icône. Tu n'as pas besoin d'ajouter un import `weight`.

### 5. Décrire chaque ligne

Entre les accolades vides de `LazyColumn`, ajoute :

```kotlin
items(visibleDestinations) { destination ->
    Text(
        text = "${destination.nom}, ${destination.pays}",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
    )
}
```

`items` demande une ligne pour chaque destination visible. À chaque passage, `destination` est la fiche de cette ligne.

`${destination.nom}` et `${destination.pays}` insèrent leurs valeurs dans le texte. La première ligne affichera **Bali, Indonésie**.

`bodyLarge` est le style de texte courant du thème. `padding(vertical = 12.dp)` ajoute de l'espace au-dessus et au-dessous de chaque ligne.

**Il doit y avoir un seul bloc `items` dans cette liste.** N'en ajoute pas un deuxième à l'intérieur du premier.

Pour te repérer : `Text(...)` se trouve dans `items`, `items` dans `LazyColumn`, et `LazyColumn` dans `Column`, sous le champ de recherche.

### 6. Corriger le texte du champ

Dans ton `OutlinedTextField`, remplace seulement :

```kotlin
label = { Text("Rechercher un destination") },
```

par :

```kotlin
label = { Text("Rechercher une destination") },
```

Les couleurs et la loupe restent comme tu les as préparées.

## TM06-7 — Préparer la fiche d’un souvenir ajouté par l’utilisateur

**Crée et ouvre `model/Journal.kt`.** Il se place dans le même groupe que Destination.kt.

Cette fiche prépare la page Journal montrée sur ta maquette. Elle décrit un souvenir créé par l’utilisateur, et non un séjour proposé dans l’accueil.

En haut du fichier, vérifie le package :

```kotlin
package com.example.tripmate.model
```

En dessous, ajoute :

```kotlin
data class Journal(
    val id: Int,
    val titre: String,
    val lieu: String,
    val pays: String,
    val dateArrivee: String,
    val dateDepart: String,
    val activites: String,
    val avis: String,
    val photos: List<String>,
)
```

Tu retrouves les types déjà utilisés : Int pour un numéro, String pour un texte et List<String> pour une liste de textes.

Chaque information a un rôle :

| Champ | Ce qu’il contiendra |
| --- | --- |
| `id` | Un numéro unique attribué par l’application, pas saisi par l’utilisateur. |
| `titre` | Le titre saisi, par exemple Mon séjour à Grand-Bassam. |
| `lieu`, `pays` | L’endroit où le voyage a eu lieu. |
| `dateArrivee`, `dateDepart` | Les dates choisies, gardées sous la forme année-mois-jour. |
| `activites` | Le récit des activités saisi par l’utilisateur. |
| `avis` | Son appréciation du voyage. |
| `photos` | Les références des photos qu’il aura choisies. La liste peut être vide. |

`photos` contient les adresses ou références qui permettent de retrouver les images. Les fichiers photo eux-mêmes ne sont pas écrits dans le JSON. Une photo choisie sur le téléphone n’a pas forcément une adresse Internet : elle peut avoir une référence locale fournie par Android.

Pour la carte du journal, la première photo pourra servir de couverture. S’il n’y en a aucune, on prévoira un visuel de remplacement. Le nombre de photos sera calculé à partir de cette liste ; pas besoin de l’enregistrer une deuxième fois.

### Comment le bouton « + » utilisera cette fiche plus tard

Voici le fonctionnement prévu, sans code à écrire à ce stade :

1. L’utilisateur touche « + » et ouvre le formulaire.
2. Il écrit son titre, le lieu, les dates, les activités et son avis, puis choisit ses photos.
3. Lorsqu’il valide, l’application attribue un identifiant et crée une fiche Journal avec ces informations.
4. Elle ajoute cette fiche à la liste des souvenirs et met la page à jour.
5. Elle enregistre le souvenir pour qu’il soit retrouvé après fermeture.

**La constante destinationData ne peut pas être modifiée par l’application.** Son JSON est un point de départ fixe, pas une base dans laquelle le bouton « + » peut écrire.

Plus tard, une liste modifiable gardera les souvenirs pendant l’utilisation. Pour les conserver après fermeture, il faudra aussi une sauvegarde sur le téléphone ou sur un serveur. Les références des photos devront elles aussi rester utilisables après fermeture.

Nous n’ajoutons pas de faux souvenirs maintenant. Le JSON reste avec `"journaux": []`. Comme cette liste est vide et que notre lecture actuelle concerne seulement les destinations, il n’y a pas encore de lecteur JSON ni de DTO de journal à écrire dans cette étape.

Créer `Journal.kt` prépare la forme des données. Cela ne fait pas apparaître automatiquement une page Journal ni un bouton d’ajout fonctionnel. Le formulaire, la liste modifiable et la sauvegarde seront construits dans les étapes correspondantes du cours.
## TM06-8 — Vérifier que cette étape est terminée

Dans Android Studio, sélectionne **app**, choisis ton téléphone ou ton émulateur, puis clique sur **Run**, le triangle vert.

Vérifie dans cet ordre :

1. La barre TripMate et ton champ sont toujours présents.
2. Sous le champ, tu vois **Bali, Indonésie** et **Santorin, Grèce**.
3. Vérifie les deux noms. Avec seulement deux destinations, il est normal que tout tienne sans défilement ; LazyColumn permettra de défiler lorsque tu ajouteras davantage de destinations.
4. Tape `bal` : seule **Bali, Indonésie** reste.
5. Efface le texte avec la touche de suppression du clavier : les deux destinations reviennent.
6. Tape `zzzz` : la liste est vide, sans fermer l'application.

Le bouton Effacer n'est pas présent dans ton écran actuel : il n'est pas nécessaire pour valider cette lecture des données.

L'aperçu `GreetingPreview` montre encore le composant Hello Android!. Il ne représente pas ton écran complet. Utilise Run pour les vérifications ci-dessus.

## Si quelque chose bloque

| Ce que tu observes | Ce qu'il faut regarder |
| --- | --- |
| `Destination` est inconnu. | Le fichier Destination.kt, son package et les imports dans les fichiers qui l'utilisent. |
| `SingleDestinationDto` reste rouge après TM06-2. | Les deux classes doivent être dans DestinationDto.kt, l'une après l'autre, pas l'une dans l'autre. |
| `loadDestinations` est inconnu dans MainActivity. | L'import `com.example.tripmate.data.loadDestinations`, puis le nom exact de la fonction. |
| La fonction doit renvoyer une valeur. | Termine TM06-5, point 4 : la ligne `return` doit être dans loadDestinations, après entries. |
| L'application se ferme pendant la lecture. | Vérifie la copie complète du JSON, les virgules et les noms exacts des clés : `destinations`, `nom`, `dateArrivee`, `dateDepart`, `commentaires`, etc. |
| Tu as modifié destinations.json mais rien ne change. | Cette étape lit destinationData dans DestinationData.kt. Mets à jour ce texte puis relance l'application. |
| La recherche ne change pas la liste. | Vérifie que le filtre lit `query` et que items utilise `visibleDestinations`, pas `destinations`. |
| `weight` est rouge. | LazyColumn doit être directement dans Column. |
| La liste contient des blocs items imbriqués. | Garde seulement le bloc TM06-6, point 5, directement dans LazyColumn. |

## Où t'arrêter

Cette étape est terminée quand Bali et Santorin s’affichent, que la recherche par nom fonctionne et que la fiche Journal est décrite. Le journal commence vide : tu n’as pas encore à remplir un formulaire ni à afficher les souvenirs.

Nous avons gardé la méthode du cours : **texte JSON → DTO → fiche de l'application → liste affichée**. Tes données d’hôtel, de prix, de dates, de tags et de commentaires sont dans les fiches. La liste initiale des journaux est vide, et Journal décrit ce que contiendra un futur souvenir.

Les identifiants et le champ d’image sont maintenant préparés. Tu ne construis pas encore les cartes illustrées, la navigation, le formulaire d’ajout du journal ou sa sauvegarde. Ce sont les prochaines fonctionnalités à apprendre, pas des conditions supplémentaires pour terminer cette étape.
