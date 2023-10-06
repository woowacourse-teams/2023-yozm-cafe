import CopyPlugin from 'copy-webpack-plugin';
import DotenvWebpackPlugin from 'dotenv-webpack';
import FaviconsWebpackPlugin from 'favicons-webpack-plugin';
import ForkTsCheckerWebpackPlugin from 'fork-ts-checker-webpack-plugin';
import HtmlWebpackPlugin from 'html-webpack-plugin';
import { dirname } from 'path';
import { fileURLToPath } from 'url';

/** @type {import('webpack').Configuration} */
export default {
  // https://github.com/TypeStrong/fork-ts-checker-webpack-plugin#installation
  context: dirname(fileURLToPath(import.meta.url)),
  entry: './src/index',
  resolve: {
    extensions: ['.js', '.jsx', '.ts', '.tsx'],
  },
  module: {
    rules: [
      {
        test: /\.[tj]sx?$/i,
        exclude: /node_modules/,
        use: 'babel-loader',
      },
    ],
  },
  plugins: [
    new DotenvWebpackPlugin({
      systemvars: true,
    }),
    new HtmlWebpackPlugin({
      template: 'index.html',
      filename: 'index.html',
    }),
    new FaviconsWebpackPlugin({
      logo: './src/assets/icon.svg',
      devMode: 'webapp',
      persistentCache: true,
      favicons: {
        icons: {
          favicons: false,
          yandex: false,
        },
        appName: '요즘카페',
        appDescription: '트렌디한 성수 지역의 카페를 손쉽게 탐색하는 서비스, 요즘카페',
        appleStatusBarStyle: '#ffd3d8',
        lang: 'ko-KR',
        background: '#ffffff',
        theme_color: '#ffd3d8',
      },
    }),
    new CopyPlugin({
      patterns: [{ from: 'public', to: '' }],
    }),
    new ForkTsCheckerWebpackPlugin(),
  ],
};
