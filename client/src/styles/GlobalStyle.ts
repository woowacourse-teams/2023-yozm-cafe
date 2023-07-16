import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`
  @import url('https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css');

  @font-face {
    font-family: 'BMJUA';
    font-weight: normal;
    font-style: normal;
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/BMJUA.woff') format('woff');
  }

  * {
    font-family: 'Pretendard', sans-serif;
  }

  body {
    position: fixed;

    display: flex;
    justify-content: center;

    width: 100vw;
    height: 100svh;
  }

  #root {
    width: 100%;
    max-width: 495px;
    border: 2px solid black;
  }
`;

export default GlobalStyle;
