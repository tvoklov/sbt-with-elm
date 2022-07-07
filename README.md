## how to use
- add [ElmCompile](project/ElmCompile.scala) to your `/project/` folder
- add `enablePlugins(ElmCompile)` in your `build.sbt`
- change [the defaults](#what-are-the-defaults) if needed

## what this does

inserts a step into sbt's compilation that compiles elm code and then puts it as a resource into the resulting jar

this also adds elm's sources to sbt's watch triggers, meaning that commands like ~run will actually work (uh, well, [read this first though](#on-continuous-compilation))

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


## on continuous compilation

elm sources are added as watch triggers to sbt's compile task. what this means is that when [continous compilation](https://www.scala-sbt.org/1.x/docs/Running.html#Continuous+build+and+test) is used, changes to elm sources will trigger a recompile (if you've used PlayFramework, it's kind of like its [development mode](https://www.playframework.com/documentation/2.8.x/PlayConsole#Development-mode) but it also includes elm files)

there is a catch though. sbt [will not stop](https://stackoverflow.com/a/49418333) any process that runs continuously, meaning something like a web server (which is probably what you will be using this template for) will not be stopped to be recompiled.

you should use something like [sbt-revolver](https://github.com/spray/sbt-revolver) in conjunction with this plugin to continously compile your web server
