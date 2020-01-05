API overview
============

## Content

- [Notes](#notes)
- [Methods](#methods)
  - [Movies](#movies)
- [Entities](#entities)

___

## Notes

###### Introduction




___

## Methods

### Movies

#### Fetching all movies:

    GET /movies?page=0&size=10&sort=title
    
| Parameter     | Description                               | Optional |
| -----------   | ----------------------------------------- | -------- |
| `page`        | Page offset                               |   Yes    |
| `size`        | Size limit / number of movies to fetch    |   Yes    |
| `sort`        | Column name for sorting                   |   Yes    |

Sort parameter should be a valid column name from [Movie](#movie) Entity. 

Returns a list of [Movie](#movie).

#### Getting a movie by Id :

    GET /movies/:id

| Attribute   | Description         |
| ----------- | ------------------- |
| `id`        | ImdbId              |

Returns a [Movie](#movie).

#### Getting a movie image by Id :

    GET /movies/image/:id

| Attribute   | Description         |
| ----------- | ------------------- |
| `id`        | ImdbId              |

Returns a String.

#### Getting a movie casting by Id :

    GET /movies/casting/:id

| Attribute   | Description         |
| ----------- | ------------------- |
| `id`        | ImdbId              |

Returns a list of [Actor](#actor).


#### Getting movies by genre :

    GET /movies/genre/:genre

| Attribute   | Description         |
| ----------- | ------------------- |
| `genre`     | movie genre         |

Returns a list of [Movies](#movie).

#### Getting movies by director :

    GET /movies/director/:director

| Attribute   | Description         |
| ----------- | ------------------- |
| `director`  | movie director      |

Returns a list of [Movies](#movie).

## Entities

#### Movie

| Attribute                | Description                                              | Nullable |
|--------------------------|----------------------------------------------------------|----------|
| `imdbid`                 | IMDB Movie ID and primary Key                            | no       |
| `title`                  | Title of the movie                                       | no       |
| `releaseyear`            | Release Year                                             | no       |
| `releasedate`            | Release Date                                             | no       |
| `genre`                  | Movie genre                                              | no       |
| `writers`                | List of writers                                          | no       |
| `actors`                 | List of actors                                           | no       |

#### Actor

| Attribute              | Description                                              | Nullable |
|------------------------|----------------------------------------------------------|----------|
| `actorid` 🔑           | Actor id and primary Key                                 | no       |
| `name`                 | Name of the actor                                        | no       |
| `moviecount`           | Number of movies done by Actor                           | no       |
| `ratingsum`            | Number of rating given by users                          | no       |
| `normalizedmovierank`  | Actor rank over others                                   | no       |
| `googlehits`           | Actor number of hits on google search                    | no       |
| `normalizedgooglerank` | Actor rank on google                                     | yes       |
| `normalizedrating`     | Actor global rating by users                             | no       |
