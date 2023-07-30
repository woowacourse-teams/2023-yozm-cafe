import type { Preview } from '@storybook/react';
import React from 'react';
import { MemoryRouter } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import GlobalStyle from '../src/styles/GlobalStyle';
import ResetStyle from '../src/styles/ResetStyle';
import theme from '../src/styles/theme';

const customViewports = {
  Default: {
    name: 'Default',
    styles: {
      width: '390px',
      height: '844px',
    },
  },
};

const preview: Preview = {
  parameters: {
    actions: { argTypesRegex: '^on[A-Z].*' },
    controls: {
      matchers: {
        color: /(background|color)$/i,
        date: /Date$/,
      },
    },
    viewport: {
      viewports: { ...customViewports },
      defaultViewport: 'Default',
    },
  },

  decorators: [
    (Story) => (
      <MemoryRouter initialEntries={['/']}>
        <ThemeProvider theme={theme}>
          <ResetStyle />
          <GlobalStyle />
          <Story />
        </ThemeProvider>
      </MemoryRouter>
    ),
  ],
};

export default preview;
