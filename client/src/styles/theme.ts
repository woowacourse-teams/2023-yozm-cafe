const theme = {
  color: {
    primary: '#F08080',
    secondary: '#FFD3D8',
    tertiary: '#f4a9a8',

    yellow: '#F7E600',
    gray: '#787878',
    black: '#000000',
    white: '#FFFFFF',

    error: '#F14646',
    warning: '#EF8523',
    success: '#31A115',

    text: {
      primary: '#212121',
    },

    line: {
      primary: '#777777',
      secondary: '#BABABA',
    },

    background: {
      primary: '#f8f8f8',
      secondary: 'rgba(0, 0, 0, 0.5);',
    },
  },
  fontSize: {
    xs: '0.75rem',
    sm: '0.875rem',
    base: '1rem',
    lg: '1.125rem',
    xl: '1.25rem',
    '2xl': '1.5rem',
    '3xl': '1.875rem',
    '4xl': '2.25rem',
    '5xl': '3rem',
    '6xl': '3.75rem',
    '7xl': '4.5rem',
  },
  space: {
    '0.5': '0.125rem',
    '1': '0.25rem',
    '1.5': '0.375rem',
    '2': '0.5rem',
    '2.5': '0.625rem',
    '3': '0.75rem',
    '3.5': '0.875rem',
    '4': '1rem',
    '5': '1.25rem',
    '6': '1.5rem',
    '7': '1.75rem',
    '8': '2rem',
    '9': '2.25rem',
    '10': '2.5rem',
    '11': '2.75rem',
    '12': '3rem',
    '14': '3.5rem',
    '16': '4rem',
    '20': '5rem',
    '24': '6rem',
    '28': '7rem',
  },
  shadow: {
    '1': '0 0 2px 2px rgba(0, 0, 0, 0.2)',
    '2': '0px 3px 8px rgba(0, 0, 0, 0.24)',
    '3': '0 0 4px 4px rgba(0, 0, 0, 0.2)',
    '4': '0 0 6px 6px rgba(0, 0, 0, 0.2)',
    '5': '0 0 8px 8px rgba(0, 0, 0, 0.2)',
    '6': '0 0 10px 10px rgba(0, 0, 0, 0.2)',
  },
} as const;

export type Theme = typeof theme;

export default theme;
