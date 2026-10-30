# Étape 06 — Utiliser tes destinations dans TripMate

## Ce que tu vas faire

Ton application affiche déjà la barre TripMate et le champ de recherche. Tu as aussi écrit les informations de tes voyages dans `destinations.json`.

Nous allons reprendre **la méthode du cours Netfloux**, avec tes destinations à la place des films :

1. Décrire les informations d'une destination dans une fiche Kotlin.
2. Préparer les fiches qui représentent les données lues dans le JSON.
3. Passer de ces fiches à celles de l'application.
4. Placer ton texte JSON dans une variable Kotlin, comme `movieData` dans le cours.
5. Lire les destinations, puis afficher leurs villes sur ton écran.

À la fin, tu verras les **10 villes de ton fichier**. Taper `par` dans la recherche laissera Paris dans la liste. Les hôtels, prix, tags et commentaires seront aussi disponibles dans les fiches, même si nous n'affichons ici que la ville et le pays.

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

Tu gardes les noms français de tes données : `ville`, `pays`, `hotel`, etc. Il n'est pas nécessaire de renommer ces informations comme les champs des films.

## Avant de commencer

Travaille dans **TripMate**, pas dans le projet Netfloux. Sauvegarde les fichiers que tu vas modifier si tu veux conserver tes essais.

Tu vas créer quatre fichiers Kotlin. Leur emplacement de départ est :

```text
app/src/main/java/com/example/tripmate/
```

| Fichier à créer à partir de cet emplacement | Son rôle |
| --- | --- |
| `model/Destination.kt` | Décrire une destination utilisée par l'application. |
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
    val ville: String,
    val pays: String,
    val hotel: String,
    val adresse: String,
    val prix: Int,
    val dateReservation: String,
    val tags: List<String>,
    val commentaires: List<String>,
)
```

Une **`data class`** regroupe des informations dans une fiche. `Destination` est le nom de cette fiche.

Chaque ligne nomme une information. Après les deux-points `:`, nous indiquons son type, c'est-à-dire ce qu'elle peut contenir :

- `String` : un texte, comme `Paris` ou `France`.
- `Int` : un nombre entier, comme `145` pour le prix de Paris dans ton fichier.
- `List<String>` : plusieurs textes, comme les tags `culture`, `gastronomie` et `patrimoine`.

`val` signifie que cette information ne sera pas remplacée dans la fiche après sa création. Les virgules séparent les informations.

Nous gardons `dateReservation` comme un texte, par exemple `12/04/2027`. Nous ne faisons pas encore de calcul avec cette date.

Le prix est un nombre dans ton JSON. Il reste donc un nombre dans Kotlin. Nous gardons tes valeurs sans décider ici si elles représentent un prix par nuit, par personne ou par séjour.

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
    val ville: String,
    val pays: String,
    val hotel: String,
    val adresse: String,
    val prix: Int,
    val dateReservation: String,
    val tags: List<String>,
    val commentaires: List<String>,
)
```

Les noms correspondent exactement aux informations de ton fichier JSON. Par exemple, `hotel` s'écrit sans accent dans le code, comme dans ton fichier.

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
    ville = dto.ville,
    pays = dto.pays,
    hotel = dto.hotel,
    adresse = dto.adresse,
    prix = dto.prix,
    dateReservation = dto.dateReservation,
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

Chaque ligne recopie une information. Par exemple, `ville = dto.ville` place la ville reçue dans la nouvelle fiche. Le point de `dto.ville` signifie « prends la ville de cette fiche ».

Le groupe `mapper` contient donc le passage d'une forme de données à l'autre. Pour tes données actuelles, ce passage est une copie. Si la forme reçue change plus tard, ce sera l'endroit où l'adapter.

## TM06-4 — Placer ton JSON dans Kotlin, comme dans le cours

**Ouvre le nouveau fichier `data/DestinationData.kt`.**

Cette étape correspond à `const val movieData = """ ... """` dans Netfloux. Nous allons mettre ton propre texte entre les triples guillemets.

### 1. Préparer le haut du fichier

Vérifie le package et ajoute ces imports :

```kotlin
package com.example.tripmate.data

import com.example.tripmate.mapper.mapDestination
import com.example.tripmate.model.Destination
import com.example.tripmate.model.DestinationDto
import com.example.tripmate.model.SingleDestinationDto
import org.json.JSONObject
```

`JSONObject` est l'outil Android qui lit un objet JSON. Il est déjà disponible : tu n'as pas de bibliothèque à ajouter et tu ne modifies pas Gradle.

### 2. Préparer la place pour le texte

Après les imports, écris ces trois lignes :

```kotlin
const val destinationData = """

"""
```

`const val` donne un nom à un texte fixe. Les trois guillemets `"""` permettent d'écrire un texte sur plusieurs lignes. La première ligne ouvre ce texte ; la dernière le ferme.

### 3. Copier tes données existantes

Ouvre maintenant ton fichier :

```text
app/src/main/java/com/example/tripmate/data/destinations.json
```

Dans **ce fichier JSON**, fais Ctrl+A puis Ctrl+C pour copier tout le contenu.

Reviens dans `DestinationData.kt`. Place le curseur sur **la ligne vide entre les deux séries de triples guillemets**, puis fais Ctrl+V.

Tu dois avoir, dans cet ordre :

1. La ligne `const val destinationData = """`.
2. Tout ton JSON, depuis sa première accolade `{` jusqu'à sa dernière accolade `}`.
3. La ligne de fermeture `"""`.

**Ne remplace pas tout le fichier Kotlin par le JSON.** Le package et les imports restent au-dessus de la constante.

Ton texte contient deux listes : `destinations` et `journaux`. Copie les deux pour conserver tes données telles quelles. Dans ce guide, notre fonction lira seulement `destinations`. Les journaux restent dans le texte et dans ton fichier d'origine ; nous ne les affichons pas encore.

Le fichier `destinations.json` peut rester à sa place. Dans cette méthode, l'application utilisera **le texte de `destinationData`**, pas le fichier `.json`. Si tu changes ensuite les données de cette étape, modifie le texte de la constante. Le fichier JSON d'origine ne met pas la constante à jour automatiquement.

Pour lire le JSON : les accolades `{ }` entourent un objet ; les crochets `[ ]` entourent une liste. Par exemple, `tags` est une liste. Les guillemets et les virgules font partie du format : garde-les pendant la copie.

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

- `array.length()` donne leur nombre : 10 dans ton fichier actuel.
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
    ville = item.getString("ville"),
    pays = item.getString("pays"),
    hotel = item.getString("hotel"),
    adresse = item.getString("adresse"),
    prix = item.getInt("prix"),
    dateReservation = item.getString("dateReservation"),
    tags = tags,
    commentaires = commentaires,
)
```

Ce morceau construit une fiche à partir des informations lues : `getString("ville")` lit la ville ; `getInt("prix")` lit le prix comme un nombre entier.

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

### 3. Garder seulement les villes recherchées

Juste sous la ligne précédente et **avant `OutlinedTextField(...)`**, ajoute :

```kotlin
val visibleDestinations = destinations.filter { destination ->
    destination.ville.contains(query, ignoreCase = true)
}
```

`filter` parcourt les destinations et garde celles qui respectent la condition. `destination` est la fiche examinée.

`destination.ville.contains(query, ignoreCase = true)` vérifie si la ville contient le texte tapé. Les majuscules et les minuscules ne font pas de différence. Avec `par`, Paris reste dans la liste. Avec un champ vide, toutes les villes restent.

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
        text = "${destination.ville}, ${destination.pays}",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
    )
}
```

`items` demande une ligne pour chaque destination visible. À chaque passage, `destination` est la fiche de cette ligne.

`${destination.ville}` et `${destination.pays}` insèrent leurs valeurs dans le texte. La première ligne affichera par exemple **Paris, France**.

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

## TM06-7 — Vérifier que cette étape est terminée

Dans Android Studio, sélectionne **app**, choisis ton téléphone ou ton émulateur, puis clique sur **Run**, le triangle vert.

Vérifie dans cet ordre :

1. La barre TripMate et ton champ sont toujours présents.
2. Sous le champ, tu vois des villes avec leur pays. La première est **Paris, France**.
3. Ferme le clavier si nécessaire et fais défiler : tu peux atteindre les 10 destinations, jusqu'à **Bali, Indonésie**.
4. Tape `par` : seule **Paris, France** reste.
5. Efface le texte avec la touche de suppression du clavier : les 10 destinations reviennent.
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
| L'application se ferme pendant la lecture. | Vérifie la copie complète du JSON, les virgules et les noms exacts des clés : `destinations`, `dateReservation`, `commentaires`, etc. |
| Tu as modifié destinations.json mais rien ne change. | Cette étape lit destinationData dans DestinationData.kt. Mets à jour ce texte puis relance l'application. |
| La recherche ne change pas la liste. | Vérifie que le filtre lit `query` et que items utilise `visibleDestinations`, pas `destinations`. |
| `weight` est rouge. | LazyColumn doit être directement dans Column. |
| La liste contient des blocs items imbriqués. | Garde seulement le bloc TM06-6, point 5, directement dans LazyColumn. |

## Où t'arrêter

Cette étape est terminée quand les 10 destinations s'affichent et que la recherche par ville fonctionne.

Nous avons gardé la méthode du cours : **texte JSON → DTO → fiche de l'application → liste affichée**. Tes données d'hôtel, de prix, de tags et de commentaires sont dans les fiches. Les journaux restent conservés dans le texte JSON.

Pour l'instant, n'ajoute pas d'adresse d'image, d'identifiant, de serveur ou de navigation pour résoudre cette étape. Ce seront des adaptations à traiter au moment correspondant du cours. Les photos et les cartes complètes de la maquette ne sont pas encore le résultat attendu ici.
