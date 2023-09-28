import CopyPlugin from 'copy-webpack-plugin';
import DotenvWebpackPlugin from 'dotenv-webpack';
import FaviconsWebpackPlugin from 'favicons-webpack-plugin';
import ForkTsCheckerWebpackPlugin from 'fork-ts-checker-webpack-plugin';
import HtmlWebpackPlugin from 'html-webpack-plugin';
import { dirname } from 'path';
import ReactFreshBabelPlugin from 'react-refresh/babel';
import { fileURLToPath } from 'url';

export const IS_DEV = process.env.NODE_ENV === 'development';

/** @type {import('webpack').Configuration} */
export default {
  // https://github.com/TypeStrong/fork-ts-checker-webpack-plugin#installation
  context: dirname(fileURLToPath(import.meta.url)),
  entry: './src/index',
  output: {
    filename: '[name].[contenthash].js',
    publicPath: '/',
    clean: true,
  },
  resolve: {
    extensions: ['.js', '.jsx', '.ts', '.tsx'],
  },
  module: {
    rules: [
      {
        test: /\.[tj]sx?$/i,
        exclude: /node_modules/,
        use: {
          loader: 'babel-loader',
          /** @type {import('@babel/core').TransformOptions} */
          options: {
            presets: [
              [
                '@babel/preset-react',
                {
                  runtime: 'automatic',
                  development: IS_DEV,
                },
              ],
              '@babel/preset-typescript',
            ],
            plugins: [IS_DEV && ReactFreshBabelPlugin].filter(Boolean),
          },
        },
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
