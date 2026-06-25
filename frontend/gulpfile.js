const { src, dest, watch, parallel, series } = require("gulp");

const scss = require("gulp-sass")(require("sass"));
const pug = require("gulp-pug");
const browserSync = require("browser-sync").create();
const autoprefixer = require("gulp-autoprefixer");
const webpack = require("webpack-stream");

function html() {
  return src("src/pug/pages/*.pug")
    .pipe(pug({ pretty: true }))
    .pipe(dest("dist"))
    .pipe(browserSync.stream());
}

function styles() {
  return src("src/scss/main.scss")
    .pipe(scss({ outputStyle: "compressed" }))
    .pipe(autoprefixer({ overrideBrowserslist: ["last 10 versions"] }))
    .pipe(dest("dist/css"))
    .pipe(browserSync.stream());
}

function scripts() {
  return src("src/js/main/main.js")
    .pipe(
      webpack({
        mode: "development",
        output: {
          filename: "main.min.js",
        },
      }),
    )
    .pipe(dest("dist/js"))
    .pipe(browserSync.stream());
}

function assets() {
  return src("src/assets/**/*")
    .pipe(dest("dist/assets"))
    .pipe(browserSync.stream());
}

function watching() {
  browserSync.init({
    server: { baseDir: "dist/" },
  });

  watch(["src/scss/**/*.scss"], styles);
  watch(["src/js/**/*.js"], scripts);
  watch(["src/pug/**/*.pug"], html);
  watch(["src/assets/**/*"], assets);
}

exports.html = html;
exports.styles = styles;
exports.scripts = scripts;
exports.assets = assets;

exports.build = series(parallel(html, styles, scripts, assets));

exports.default = series(exports.build, watching);
