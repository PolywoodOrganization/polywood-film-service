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



###### Errors



######

Localhost:8080/ is use there as default API URI. If the API is on a remote host or on a different port don't forget to change it.


___

## Methods

### Movies

#### Fetching all movies:

    GET /localhost:8080/movies?page=0&size=10&sort=title
    
| Parameter     | Description                               | Optional |
| -----------   | ----------------------------------------- | -------- |
| `page`        | Page offset                               |   Yes    |
| `size`        | Size limit / number of movies to fetch    |   Yes    |
| `sort`        | Column name for sorting                   |   Yes    |

Sort parameter should be a valid column name from [Movie](#movie) Entity. 

Returns a [Movie](#movie).

#### Getting a movie by Id :

    GET /localhost:8080/movies/:id

| Attribute   | Description         |
| ----------- | ------------------- |
| `id`        | ImdbId              |

#### Getting a movie image by Id :

    GET /localhost:8080/movies/image/:id

| Attribute   | Description         |
| ----------- | ------------------- |
| `id`        | ImdbId              |


## Entities

#### Movie

| Attribute                | Description                                              | Nullable |
|--------------------------|----------------------------------------------------------|----------|
| `ImdbId`                 | IMDB Movie ID and primary Key                            | no       |
| `title`                  | Title of the movie                                       | no       |
| `releaseYear`            | Release Year                                             | no       |
| `releaseDate`            | Release Date                                             | no       |
| `genre`                  | Movie genre                                              | no       |
| `writers`                | List of writers                                          | no       |
| `actors`                 | List of actors                                           | no       |





> **Note:** Work in progress
