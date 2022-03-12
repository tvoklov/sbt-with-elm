## what this does
inserts a step into sbt's compilation that compiles elm code and then puts it as a resource into the resulting jar

## what for
to package both front and backend code into one jar

## what are the defaults
- elm code in `src/main/elm`
- scala code in `src/main/scala` (duh)
- path to compiled js inside of the jar is `elm/elm.js`

## what you can use this for
read through [`build.sbt`](build.sbt) if you want to learn one way to make sbt compile your elm/ts/whatever code and put it right into your target/jar,
all in a clean, non-hardcoded, no ".gitignore the compiled junk" way

### OR

- fork this repo
- tweak some stuff (like, change `elmMainFile`)
- write your code

all without a single care for what lies beyond the ["elm compilation magic"](build.sbt#L15) comment
