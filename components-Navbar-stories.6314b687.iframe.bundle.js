"use strict";(self.webpackChunkyozm_cafe=self.webpackChunkyozm_cafe||[]).push([[425],{"./src/components/Navbar.stories.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Default:()=>Default,__namedExportsOrder:()=>__namedExportsOrder,default:()=>Navbar_stories});var dist=__webpack_require__("./node_modules/react-router-dom/dist/index.js"),styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),useQuery=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/useQuery.mjs");class ClientNetworkError extends Error{constructor(){super("인터넷에 연결할 수 없습니다")}}const src_client=new class Client{accessToken=null;async fetch(input,init){try{const response=await fetch(`/api${input}`,{...init,headers:{...init?.headers,...this.accessToken?{Authorization:`Bearer ${this.accessToken}`}:{}}});if(!response.ok)throw response;return response.json()}catch(error){throw new ClientNetworkError}}setAccessToken(accessToken){this.accessToken=accessToken}getCafes(page=1){return this.fetch(`/cafes?page=${page}`)}getUser(userId){return this.fetch(`/members/${userId}`)}addFavoriteCafe(cafeId){return this.fetch(`/cafes/${cafeId}/likes`,{method:"POST"})}removeFavoriteCafe(cafeId){return this.fetch(`/cafes/${cafeId}/likes`,{method:"DELETE"})}issueAccessToken(provider,code){return this.fetch(`/auth/${provider}?code=${code}`,{method:"POST"}).then((data=>data.token))}};var useMutation=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/useMutation.mjs"),react=__webpack_require__("./node_modules/react/index.js"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const AuthContext=(0,react.createContext)(null),AuthProvider=props=>{const{children}=props,[identity,setIdentity]=(0,react.useState)(null),contextValue=(0,react.useMemo)((()=>({identity,setIdentity})),[identity]);return(0,jsx_runtime.jsx)(AuthContext.Provider,{value:contextValue,children})};AuthProvider.displayName="AuthProvider";const context_AuthContext=AuthContext;try{AuthProvider.displayName="AuthProvider",AuthProvider.__docgenInfo={description:"",displayName:"AuthProvider",props:{}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/context/AuthContext.tsx#AuthProvider"]={docgenInfo:AuthProvider.__docgenInfo,name:"AuthProvider",path:"src/context/AuthContext.tsx#AuthProvider"})}catch(__react_docgen_typescript_loader_error){}const hooks_useAuth=()=>{const authContext=(0,react.useContext)(context_AuthContext);if(!authContext)throw new Error("AuthProvider를 사용해주셔야 합니다.");const{identity,setIdentity}=authContext,{mutate:issueAccessToken}=(0,useMutation.D)({mutationKey:["auth"],mutationFn:({provider,code})=>src_client.issueAccessToken(provider,code).then((accessToken=>(src_client.setAccessToken(accessToken),accessToken))),onSettled:accessToken=>{if(!accessToken)return null;const[header,payload,verifySignature]=accessToken.split("."),identity=JSON.parse(window.atob(payload));setIdentity(identity)}});return{identity,issueAccessToken}},hooks_useUser=()=>{const{identity}=hooks_useAuth();return(0,useQuery.a)({queryKey:["user",identity],queryFn:()=>src_client.getUser(identity?.sub),enabled:!!identity})};var Button=__webpack_require__("./src/components/Button.tsx"),Logo=__webpack_require__("./src/components/Logo.tsx");const Navbar=()=>{const{data:user}=hooks_useUser();return(0,jsx_runtime.jsxs)(Container,{children:[(0,jsx_runtime.jsx)(LogoContainer,{to:"/",children:(0,jsx_runtime.jsx)(Logo.Z,{fontSize:"4xl"})}),(0,jsx_runtime.jsx)(ButtonContainer,{children:user?(0,jsx_runtime.jsx)(ProfileImage,{src:user.imageUrl,alt:"Profile"}):(0,jsx_runtime.jsx)(Button.Z,{fullWidth:!0,fullHeight:!0,children:"로그인"})})]})};Navbar.displayName="Navbar";const components_Navbar=Navbar,Container=styled_components_browser_esm.zo.nav`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  height: 66px;
  padding: 0 15px;
`,ButtonContainer=styled_components_browser_esm.zo.div`
  flex: 2;
  height: 55%;
`,LogoContainer=(0,styled_components_browser_esm.zo)(dist.rU)`
  flex: 8;
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
`;try{Button.displayName="Button",Button.__docgenInfo={description:"",displayName:"Button",props:{variant:{defaultValue:{value:"default"},description:"",name:"variant",required:!1,type:{name:"enum",value:[{value:'"disabled"'},{value:'"default"'},{value:'"outlined"'}]}},fullWidth:{defaultValue:{value:"false"},description:"",name:"fullWidth",required:!1,type:{name:"boolean"}},fullHeight:{defaultValue:{value:"false"},description:"",name:"fullHeight",required:!1,type:{name:"boolean"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/Button.tsx#Button"]={docgenInfo:Button.__docgenInfo,name:"Button",path:"src/components/Button.tsx#Button"})}catch(__react_docgen_typescript_loader_error){}},"./src/components/Logo.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Z:()=>__WEBPACK_DEFAULT_EXPORT__});var styled_components__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),_styles_theme__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/styles/theme.ts"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./node_modules/react/jsx-runtime.js");const Logo=({fontSize})=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(StyledLogo,{fontSize,children:"요즘카페"});Logo.displayName="Logo";const __WEBPACK_DEFAULT_EXPORT__=Logo,StyledLogo=styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.h1`
  font-family: 'BMJUA', sans-serif;
  font-size: ${props=>_styles_theme__WEBPACK_IMPORTED_MODULE_0__.Z.fontSize[props.fontSize]};
  color: ${({theme})=>theme.color.primary};
`;try{Logo.displayName="Logo",Logo.__docgenInfo={description:"",displayName:"Logo",props:{fontSize:{defaultValue:null,description:"",name:"fontSize",required:!0,type:{name:"enum",value:[{value:'"xs"'},{value:'"sm"'},{value:'"base"'},{value:'"lg"'},{value:'"xl"'},{value:'"2xl"'},{value:'"3xl"'},{value:'"4xl"'},{value:'"5xl"'},{value:'"6xl"'},{value:'"7xl"'}]}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/Logo.tsx#Logo"]={docgenInfo:Logo.__docgenInfo,name:"Logo",path:"src/components/Logo.tsx#Logo"})}catch(__react_docgen_typescript_loader_error){}}}]);
//# sourceMappingURL=components-Navbar-stories.6314b687.iframe.bundle.js.map