#!/usr/bin/env node

import { program } from 'commander';
import webpack from 'webpack';
import WebpackDevServer from 'webpack-dev-server';
import webpackConfig from '../webpack.development.js';

process.env.NODE_ENV = 'development';

/** @type {Record<string, import('webpack-dev-server').ProxyConfigMap | null>} */
const profiles = {
  msw: {},
  local: {
    '/api': {
      changeOrigin: true,
      target: 'http://localhost:8080',
      pathRewrite: {
        '^/api': '',
      },
    },
  },
  dev: {
    '/api': {
      changeOrigin: true,
      target: 'https://dev.yozm.cafe',
    },
    '/images': {
      changeOrigin: true,
      target: 'https://dev.yozm.cafe',
    },
  },
  prod: {
    '/api': {
      changeOrigin: true,
      target: 'https://yozm.cafe',
    },
    '/images': {
      changeOrigin: true,
      target: 'https://yozm.cafe',
    },
  },
};

program.option(
  '--profile <profile>',
  `API 서버 프로필을 지정합니다. ${Object.keys(profiles).join(', ')} 중 하나를 선택할 수 있습니다`,
  'msw',
);
program.parse();

const options = program.opts();
const profile = profiles[options.profile];

if (!profile) {
  program.error(
    `"${options.profile}"는 존재하지 않는 프로필입니다. ${Object.keys(profiles).join(', ')} 중 하나를 선택해주세요.`,
  );
}

if (options.profile === 'msw') {
  process.env.MSW = 'true';
}

const compiler = webpack(webpackConfig);
const devServerConfig = {
  ...webpackConfig.devServer,
  proxy: profile,
};
const server = new WebpackDevServer(devServerConfig, compiler);

const runServer = async () => {
  console.log(`Starting server with profile ${options.profile}`);
  await server.start();
};

runServer();
