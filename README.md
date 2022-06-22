## what this does

inserts a step into sbt's compilation that compiles elm code and then puts it as a resource into the resulting jar

## what for

to package both front and backend code into one jar

## what are the defaults

| sbt key         | description                             | default        |
|-----------------|-----------------------------------------|----------------|
| elmSrcDirectory | elm source root                         | `src/main/elm` |
| elmMainFile     | elm main file (relative to source root) | `Main.elm`     |
| elmOutputFile   | compiled js location in jar             | `elm/elm.js`   |

## what are the requirements

- [`elm`](https://guide.elm-lang.org/install/elm.html) to be installed and be accessible from your system's terminal

## what you can use this for

read through [the plugin file](project/ElmCompile.scala) if you want to learn one way
to make sbt compile your elm/ts/whatever code and put it right into your jar.
all in a clean, non-hardcoded, no ".gitignore the compiled junk" way

### OR

- fork this repo
- tweak some stuff (see: [what are the defaults](#what-are-the-defaults))
- write your code
