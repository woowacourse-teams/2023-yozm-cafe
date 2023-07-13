import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`
  @import url('https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css');

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
