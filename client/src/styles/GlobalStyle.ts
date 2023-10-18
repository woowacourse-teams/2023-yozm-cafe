import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`
  @font-face {
    font-family: 'Pretendard-Regular';
    font-weight: 400;
    font-style: normal;
    src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff') format('woff');
  }

  * {
    scrollbar-width: none;
    font-family: 'Pretendard', sans-serif;

    &::-webkit-scrollbar {
      display: none;
    }
  }

  body {
    position: fixed;

    overflow: hidden;
    display: flex;
    justify-content: center;

    width: 100vw;
    height: 100svh;

  }

  button {
    cursor: pointer;
  }

  #root {
    position: relative;

    overflow-y: auto;
    overscroll-behavior-y: contain;
    display: flex;
    flex-direction: column;

    width: 100%;
    max-width: 495px;

    box-shadow: ${({ theme }) => theme.shadow['2']};

    -webkit-overflow-scrolling: touch;
  }
`;

export default GlobalStyle;
