"use strict";(self.webpackChunkyozm_cafe=self.webpackChunkyozm_cafe||[]).push([[639],{"./src/components/LoginModal.stories.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Default:()=>Default,__namedExportsOrder:()=>__namedExportsOrder,default:()=>LoginModal_stories});var chunk_OPEUWD42=__webpack_require__("./node_modules/@storybook/addon-actions/dist/chunk-OPEUWD42.mjs");const LoginModal_stories={title:"LoginModal",component:__webpack_require__("./src/components/LoginModal.tsx").Z},Default={args:{onClose:(0,chunk_OPEUWD42.aD)("close")}};Default.parameters={...Default.parameters,docs:{...Default.parameters?.docs,source:{originalSource:"{\n  args: {\n    onClose: action('close')\n  }\n}",...Default.parameters?.docs?.source}}};const __namedExportsOrder=["Default"]},"./src/components/LoginButton.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Z:()=>__WEBPACK_DEFAULT_EXPORT__});var styled_components__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),_styles_theme__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/styles/theme.ts"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./node_modules/react/jsx-runtime.js");const LoginButton=({children,...rest})=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(Container,{...rest,children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(ButtonContent,{children})});LoginButton.displayName="LoginButton";const __WEBPACK_DEFAULT_EXPORT__=LoginButton,Container=styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.button`
  cursor: pointer;

  width: 44px;
  height: 44px;

  font-size: ${({theme})=>theme.fontSize.base};
  font-weight: 500;

  background-color: ${props=>_styles_theme__WEBPACK_IMPORTED_MODULE_0__.Z.color[props.$color]};
  border: none;
  border-radius: 4px;
  box-shadow: ${({theme})=>theme.shadow[1]};
`,ButtonContent=styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.div`
  display: flex;
  justify-content: center;
`;try{LoginButton.displayName="LoginButton",LoginButton.__docgenInfo={description:"",displayName:"LoginButton",props:{$color:{defaultValue:null,description:"",name:"$color",required:!0,type:{name:"enum",value:[{value:'"text"'},{value:'"primary"'},{value:'"secondary"'},{value:'"yellow"'},{value:'"gray"'},{value:'"white"'},{value:'"error"'},{value:'"warning"'},{value:'"success"'},{value:'"line"'},{value:'"background"'}]}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/LoginButton.tsx#LoginButton"]={docgenInfo:LoginButton.__docgenInfo,name:"LoginButton",path:"src/components/LoginButton.tsx#LoginButton"})}catch(__react_docgen_typescript_loader_error){}},"./src/components/LoginModal.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Z:()=>__WEBPACK_DEFAULT_EXPORT__});var react_icons_cg__WEBPACK_IMPORTED_MODULE_4__=__webpack_require__("./node_modules/react-icons/cg/index.esm.js"),styled_components__WEBPACK_IMPORTED_MODULE_3__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),_LoginButton__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/components/LoginButton.tsx"),_Logo__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./src/components/Logo.tsx"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./node_modules/react/jsx-runtime.js");const LoginModal=({onClose})=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(ModalContainer,{onClick:onClose,children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsxs)(ModalContent,{onClick:e=>{e.stopPropagation()},children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(CloseButtonContainer,{children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(CloseIcon,{onClick:onClose})}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(_Logo__WEBPACK_IMPORTED_MODULE_1__.Z,{fontSize:"5xl"}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(LoginTitle,{children:"간편 로그인"}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsxs)(ButtonContainer,{children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)("a",{href:"/auth/kakao?code=1234",children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(_LoginButton__WEBPACK_IMPORTED_MODULE_0__.Z,{$color:"yellow",children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)("img",{src:"/assets/kakao.svg",alt:"카카오 로고"})})}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)("a",{href:"/api/auth/google",children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(_LoginButton__WEBPACK_IMPORTED_MODULE_0__.Z,{$color:"white",children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)("img",{src:"/assets/google.svg",alt:"구글 로고"})})})]})]})});LoginModal.displayName="LoginModal";const __WEBPACK_DEFAULT_EXPORT__=LoginModal,ModalContainer=styled_components__WEBPACK_IMPORTED_MODULE_3__.zo.div`
  position: fixed;
  z-index: 999;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 100%;

  background: ${({theme})=>theme.color.background.secondary};
`,ModalContent=styled_components__WEBPACK_IMPORTED_MODULE_3__.zo.div`
  cursor: default;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  width: 350px;
  padding: ${({theme})=>theme.space[5]};

  background-color: white;
  border-radius: 8px;
`,CloseButtonContainer=styled_components__WEBPACK_IMPORTED_MODULE_3__.zo.div`
  display: flex;
  justify-content: flex-end;

  width: 100%;
  margin-bottom: ${({theme})=>theme.space[2.5]};

  color: ${({theme})=>theme.color.gray};
`,CloseIcon=(0,styled_components__WEBPACK_IMPORTED_MODULE_3__.zo)(react_icons_cg__WEBPACK_IMPORTED_MODULE_4__.Fk5)`
  cursor: pointer;
`,LoginTitle=styled_components__WEBPACK_IMPORTED_MODULE_3__.zo.div`
  display: flex;
  gap: ${({theme})=>theme.space[1.5]};
  align-items: center;

  width: 100%;
  margin-top: ${({theme})=>theme.space[2.5]};
  margin-bottom: ${({theme})=>theme.space[4]};

  font-size: ${({theme})=>theme.fontSize.xs};
  color: ${({theme})=>theme.color.gray};

  /* ::before 스타일링 */
  &::before {
    content: '';
    flex: 1;
    height: 1px;
    background: ${({theme})=>theme.color.gray};
  }

  /* ::after 스타일링 */
  &::after {
    content: '';
    flex: 1;
    height: 1px;
    background: ${({theme})=>theme.color.gray};
  }
`,ButtonContainer=styled_components__WEBPACK_IMPORTED_MODULE_3__.zo.section`
  display: flex;
  justify-content: space-evenly;
  width: 100%;
`;try{LoginModal.displayName="LoginModal",LoginModal.__docgenInfo={description:"",displayName:"LoginModal",props:{onClose:{defaultValue:null,description:"",name:"onClose",required:!0,type:{name:"() => void"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/LoginModal.tsx#LoginModal"]={docgenInfo:LoginModal.__docgenInfo,name:"LoginModal",path:"src/components/LoginModal.tsx#LoginModal"})}catch(__react_docgen_typescript_loader_error){}},"./src/components/Logo.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Z:()=>__WEBPACK_DEFAULT_EXPORT__});var styled_components__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),_styles_theme__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/styles/theme.ts"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./node_modules/react/jsx-runtime.js");const Logo=({fontSize})=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(Container,{fontSize,children:"요즘카페"});Logo.displayName="Logo";const __WEBPACK_DEFAULT_EXPORT__=Logo,Container=styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.h1`
  cursor: pointer;
  font-family: 'BMJUA', sans-serif;
  font-size: ${props=>_styles_theme__WEBPACK_IMPORTED_MODULE_0__.Z.fontSize[props.fontSize]};
  color: ${({theme})=>theme.color.primary};
`;try{Logo.displayName="Logo",Logo.__docgenInfo={description:"",displayName:"Logo",props:{fontSize:{defaultValue:null,description:"",name:"fontSize",required:!0,type:{name:"enum",value:[{value:'"xs"'},{value:'"sm"'},{value:'"base"'},{value:'"lg"'},{value:'"xl"'},{value:'"2xl"'},{value:'"3xl"'},{value:'"4xl"'},{value:'"5xl"'},{value:'"6xl"'},{value:'"7xl"'}]}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/Logo.tsx#Logo"]={docgenInfo:Logo.__docgenInfo,name:"Logo",path:"src/components/Logo.tsx#Logo"})}catch(__react_docgen_typescript_loader_error){}}}]);
//# sourceMappingURL=components-LoginModal-stories.ce0f80f8.iframe.bundle.js.map