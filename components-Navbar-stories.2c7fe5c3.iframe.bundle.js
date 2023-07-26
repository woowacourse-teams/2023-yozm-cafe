"use strict";(self.webpackChunkyozm_cafe=self.webpackChunkyozm_cafe||[]).push([[425],{"./src/components/Navbar.stories.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Default:()=>Default,__namedExportsOrder:()=>__namedExportsOrder,default:()=>Navbar_stories});var react=__webpack_require__("./node_modules/react/index.js"),dist=__webpack_require__("./node_modules/react-router-dom/dist/index.js"),styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),useQuery=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/useQuery.mjs");class ClientNetworkError extends Error{constructor(){super("인터넷에 연결할 수 없습니다")}}const src_client=new class Client{accessToken=null;async fetch(input,init){try{const response=await fetch(`/api${input}`,{...init,headers:{...init?.headers,...this.accessToken?{Authorization:`Bearer ${this.accessToken}`}:{}}});if(!response.ok)throw response;return response}catch(error){throw new ClientNetworkError}}async fetchJson(input,init){return this.fetch(input,init).then((response=>response.json()))}setAccessToken(accessToken){this.accessToken=accessToken}getCafes(page=1){return this.fetchJson(`/cafes?page=${page}`)}getUser(userId){return this.fetchJson(`/members/${userId}`)}addFavoriteCafe(cafeId){return this.fetchJson(`/cafes/${cafeId}/likes`,{method:"POST"})}removeFavoriteCafe(cafeId){return this.fetchJson(`/cafes/${cafeId}/likes`,{method:"DELETE"})}issueAccessToken(provider,code){return this.fetchJson(`/auth/${provider}?code=${code}`,{method:"POST"}).then((data=>data.token))}async clearRefreshToken(){await this.fetch("/auth",{method:"DELETE"})}};var useMutation=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/useMutation.mjs"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const AuthContext=(0,react.createContext)(null),AuthProvider=props=>{const{children}=props,[identity,setIdentity]=(0,react.useState)(null),contextValue=(0,react.useMemo)((()=>({identity,setIdentity})),[identity]);return(0,jsx_runtime.jsx)(AuthContext.Provider,{value:contextValue,children})};AuthProvider.displayName="AuthProvider";const context_AuthContext=AuthContext;try{AuthProvider.displayName="AuthProvider",AuthProvider.__docgenInfo={description:"",displayName:"AuthProvider",props:{}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/context/AuthContext.tsx#AuthProvider"]={docgenInfo:AuthProvider.__docgenInfo,name:"AuthProvider",path:"src/context/AuthContext.tsx#AuthProvider"})}catch(__react_docgen_typescript_loader_error){}const hooks_useAuth=()=>{const authContext=(0,react.useContext)(context_AuthContext);if(!authContext)throw new Error("AuthProvider를 사용해주셔야 합니다.");const{identity,setIdentity}=authContext,{mutate:issueAccessToken}=(0,useMutation.D)({mutationFn:({provider,code})=>src_client.issueAccessToken(provider,code).then((accessToken=>(src_client.setAccessToken(accessToken),accessToken))),onSettled:accessToken=>{if(!accessToken)return null;const[header,payload,verifySignature]=accessToken.split("."),identity=JSON.parse(window.atob(payload));setIdentity(identity)}}),{mutate:clearRefreshToken}=(0,useMutation.D)({mutationFn:()=>src_client.clearRefreshToken()});return{identity,issueAccessToken,clearAuthorization:(0,react.useCallback)((()=>(setIdentity(null),src_client.setAccessToken(null),clearRefreshToken())),[clearRefreshToken])}},hooks_useUser=()=>{const{identity}=hooks_useAuth();return(0,useQuery.a)({queryKey:["user",identity],queryFn:()=>src_client.getUser(identity?.sub),enabled:!!identity})};var Button=__webpack_require__("./src/components/Button.tsx"),Logo=__webpack_require__("./src/components/Logo.tsx"),Modal=__webpack_require__("./src/components/Modal.tsx");const Navbar=()=>{const{data:user}=hooks_useUser(),[isModalOpen,setIsModalOpen]=(0,react.useState)(!1);return(0,jsx_runtime.jsxs)(Container,{children:[(0,jsx_runtime.jsx)(LogoContainer,{to:"/",children:(0,jsx_runtime.jsx)(Logo.Z,{fontSize:"4xl"})}),(0,jsx_runtime.jsx)(ButtonContainer,{children:user?(0,jsx_runtime.jsx)(ProfileImage,{src:user.imageUrl,alt:"Profile"}):(0,jsx_runtime.jsx)(Button.Z,{fullWidth:!0,fullHeight:!0,onClick:()=>{setIsModalOpen(!0)},children:"로그인"})}),isModalOpen&&(0,jsx_runtime.jsx)(Modal.Z,{onClose:()=>{setIsModalOpen(!1)}})]})};Navbar.displayName="Navbar";const components_Navbar=Navbar,Container=styled_components_browser_esm.zo.nav`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  height: 66px;
  padding: 0 15px;
`,ButtonContainer=styled_components_browser_esm.zo.div`
  width: 25%;
`,LogoContainer=(0,styled_components_browser_esm.zo)(dist.rU)`
  text-decoration: none;
`,ProfileImage=styled_components_browser_esm.zo.img`
  height: 100%;
  border-radius: 100%;
`,Navbar_stories={title:"Navbar",component:components_Navbar},Default={args:{}};Default.parameters={...Default.parameters,docs:{...Default.parameters?.docs,source:{originalSource:"{\n  args: {}\n}",...Default.parameters?.docs?.source}}};const __namedExportsOrder=["Default"]},"./src/components/Button.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Z:()=>__WEBPACK_DEFAULT_EXPORT__});var styled_components__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/react/jsx-runtime.js");const Button=({children,variant="default",fullWidth=!1,fullHeight=!1,...rest})=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(Container,{variant,fullWidth,fullHeight,...rest,children});Button.displayName="Button";const __WEBPACK_DEFAULT_EXPORT__=Button,ButtonVariants={disabled:styled_components__WEBPACK_IMPORTED_MODULE_1__.iv`
    color: ${props=>props.theme.color.white};
    background-color: ${props=>props.theme.color.gray};
    border: none;
  `,outlined:styled_components__WEBPACK_IMPORTED_MODULE_1__.iv`
    color: ${props=>props.theme.color.gray};
    background-color: ${props=>props.theme.color.white};
    border: 2px solid ${props=>props.theme.color.primary};
  `,default:styled_components__WEBPACK_IMPORTED_MODULE_1__.iv`
    color: ${props=>props.theme.color.white};
    background-color: ${props=>props.theme.color.primary};
    border: none;
  `},Container=styled_components__WEBPACK_IMPORTED_MODULE_1__.ZP.button`
  cursor: pointer;

  padding: ${({theme})=>theme.space[1.5]} 0;

  font-size: 16px;
  font-weight: 500;

  border-radius: 40px;
  ${props=>ButtonVariants[props.variant||"default"]}
  ${props=>props.fullWidth&&"width: 100%;"}
  ${props=>props.fullHeight&&"height: 100%;"}
`;try{Button.displayName="Button",Button.__docgenInfo={description:"",displayName:"Button",props:{variant:{defaultValue:{value:"default"},description:"",name:"variant",required:!1,type:{name:"enum",value:[{value:'"disabled"'},{value:'"default"'},{value:'"outlined"'}]}},fullWidth:{defaultValue:{value:"false"},description:"",name:"fullWidth",required:!1,type:{name:"boolean"}},fullHeight:{defaultValue:{value:"false"},description:"",name:"fullHeight",required:!1,type:{name:"boolean"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/Button.tsx#Button"]={docgenInfo:Button.__docgenInfo,name:"Button",path:"src/components/Button.tsx#Button"})}catch(__react_docgen_typescript_loader_error){}},"./src/components/LoginButton.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Z:()=>__WEBPACK_DEFAULT_EXPORT__});var styled_components__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),_styles_theme__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/styles/theme.ts"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./node_modules/react/jsx-runtime.js");const LoginButton=({children,...rest})=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(Container,{...rest,children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(ButtonContent,{children})});LoginButton.displayName="LoginButton";const __WEBPACK_DEFAULT_EXPORT__=LoginButton,Container=styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.button`
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
`;try{LoginButton.displayName="LoginButton",LoginButton.__docgenInfo={description:"",displayName:"LoginButton",props:{$color:{defaultValue:null,description:"",name:"$color",required:!0,type:{name:"enum",value:[{value:'"text"'},{value:'"primary"'},{value:'"secondary"'},{value:'"yellow"'},{value:'"gray"'},{value:'"white"'},{value:'"error"'},{value:'"warning"'},{value:'"success"'},{value:'"line"'},{value:'"background"'}]}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/LoginButton.tsx#LoginButton"]={docgenInfo:LoginButton.__docgenInfo,name:"LoginButton",path:"src/components/LoginButton.tsx#LoginButton"})}catch(__react_docgen_typescript_loader_error){}},"./src/components/Logo.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Z:()=>__WEBPACK_DEFAULT_EXPORT__});var styled_components__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),_styles_theme__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/styles/theme.ts"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./node_modules/react/jsx-runtime.js");const Logo=({fontSize})=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(Container,{fontSize,children:"요즘카페"});Logo.displayName="Logo";const __WEBPACK_DEFAULT_EXPORT__=Logo,Container=styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.h1`
  cursor: pointer;
  font-family: 'BMJUA', sans-serif;
  font-size: ${props=>_styles_theme__WEBPACK_IMPORTED_MODULE_0__.Z.fontSize[props.fontSize]};
  color: ${({theme})=>theme.color.primary};
`;try{Logo.displayName="Logo",Logo.__docgenInfo={description:"",displayName:"Logo",props:{fontSize:{defaultValue:null,description:"",name:"fontSize",required:!0,type:{name:"enum",value:[{value:'"xs"'},{value:'"sm"'},{value:'"base"'},{value:'"lg"'},{value:'"xl"'},{value:'"2xl"'},{value:'"3xl"'},{value:'"4xl"'},{value:'"5xl"'},{value:'"6xl"'},{value:'"7xl"'}]}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/Logo.tsx#Logo"]={docgenInfo:Logo.__docgenInfo,name:"Logo",path:"src/components/Logo.tsx#Logo"})}catch(__react_docgen_typescript_loader_error){}},"./src/components/Modal.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Z:()=>__WEBPACK_DEFAULT_EXPORT__});var react_icons_cg__WEBPACK_IMPORTED_MODULE_4__=__webpack_require__("./node_modules/react-icons/cg/index.esm.js"),styled_components__WEBPACK_IMPORTED_MODULE_3__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),_LoginButton__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/components/LoginButton.tsx"),_Logo__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./src/components/Logo.tsx"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./node_modules/react/jsx-runtime.js");const Modal=({onClose})=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(ModalContainer,{onClick:onClose,children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsxs)(ModalContent,{onClick:e=>{e.stopPropagation()},children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(CloseButtonContainer,{children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(CloseIcon,{onClick:onClose})}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(_Logo__WEBPACK_IMPORTED_MODULE_1__.Z,{fontSize:"5xl"}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(LoginTitle,{children:"간편 로그인"}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsxs)(ButtonContainer,{children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)("a",{href:"/auth/kakao?code=1234",children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(_LoginButton__WEBPACK_IMPORTED_MODULE_0__.Z,{$color:"yellow",children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)("img",{src:"/assets/kakao.svg",alt:"카카오 로고"})})}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)("a",{href:"/api/auth/google",children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(_LoginButton__WEBPACK_IMPORTED_MODULE_0__.Z,{$color:"white",children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)("img",{src:"/assets/google.svg",alt:"구글 로고"})})})]})]})});Modal.displayName="Modal";const __WEBPACK_DEFAULT_EXPORT__=Modal,ModalContainer=styled_components__WEBPACK_IMPORTED_MODULE_3__.zo.div`
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
`;try{Modal.displayName="Modal",Modal.__docgenInfo={description:"",displayName:"Modal",props:{onClose:{defaultValue:null,description:"",name:"onClose",required:!0,type:{name:"() => void"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/Modal.tsx#Modal"]={docgenInfo:Modal.__docgenInfo,name:"Modal",path:"src/components/Modal.tsx#Modal"})}catch(__react_docgen_typescript_loader_error){}}}]);
//# sourceMappingURL=components-Navbar-stories.2c7fe5c3.iframe.bundle.js.map