import CopyPlugin from 'copy-webpack-plugin';
import DotenvWebpackPlugin from 'dotenv-webpack';
import ForkTsCheckerWebpackPlugin from 'fork-ts-checker-webpack-plugin';
import HtmlWebpackPlugin from 'html-webpack-plugin';
import { dirname } from 'path';
import { fileURLToPath } from 'url';

const API_URL = process.env.API_URL ?? 'http://localhost:8080';

/** @type {import('webpack').Configuration} */
export default {
  mode: 'development',
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
    new CopyPlugin({
      patterns: [{ from: 'public', to: '' }],
    }),
    new ForkTsCheckerWebpackPlugin(),
  ],
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
