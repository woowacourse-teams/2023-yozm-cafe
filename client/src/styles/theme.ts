const theme = {
  color: {
    primary: '#F08080',
    secondary: '#FFD3D8',
    error: '#F14646',
    warning: '#EF8523',
    success: '#31A115',
    text: {
      primary: '#212121',
      secondary: '#787878',
    },
    line: {
      primary: '#777777',
      secondary: '#BABABA',
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
  },
  shadow: {
    '1': '0 0 2px 2px rgba(0, 0, 0, 0.2)',
    '2': '0 0 4px 4px rgba(0, 0, 0, 0.2)',
    '3': '0 0 6px 6px rgba(0, 0, 0, 0.2)',
    '4': '0 0 8px 8px rgba(0, 0, 0, 0.2)',
    '5': '0 0 10px 10px rgba(0, 0, 0, 0.2)',
  },
} as const;

export default theme;
