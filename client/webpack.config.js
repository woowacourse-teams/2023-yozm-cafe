import ReactRefreshPlugin from '@pmmmwh/react-refresh-webpack-plugin';
import CopyPlugin from 'copy-webpack-plugin';
import DotenvWebpackPlugin from 'dotenv-webpack';
import ForkTsCheckerWebpackPlugin from 'fork-ts-checker-webpack-plugin';
import HtmlWebpackPlugin from 'html-webpack-plugin';
import { dirname } from 'path';
import ReactFreshBabelPlugin from 'react-refresh/babel';
import { fileURLToPath } from 'url';

const API_URL = process.env.API_URL ?? 'http://localhost:8080';

const IS_DEV = process.env.NODE_ENV === 'development';

/** @type {import('webpack').Configuration} */
export default {
  mode: IS_DEV ? 'development' : 'production',
  // https://github.com/TypeStrong/fork-ts-checker-webpack-plugin#installation
  context: dirname(fileURLToPath(import.meta.url)),
  entry: './src/index',
  output: {
    filename: 'bundle.js',
    publicPath: '/',
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
    IS_DEV && new ReactRefreshPlugin(),
  ].filter(Boolean),
  devServer: {
    hot: true,
    port: 3000,
    allowedHosts: 'all',
    historyApiFallback: true,
    open: true,
    proxy: {
      '/api': {
        target: API_URL,
        pathRewrite: {
          '^/api': '',
        },
      },
    },
  },
};
