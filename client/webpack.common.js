import CopyPlugin from 'copy-webpack-plugin';
import DotenvWebpackPlugin from 'dotenv-webpack';
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
    new CopyPlugin({
      patterns: [{ from: 'public', to: '' }],
    }),
    new ForkTsCheckerWebpackPlugin(),
  ],
};
