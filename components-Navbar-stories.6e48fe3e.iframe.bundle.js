"use strict";(self.webpackChunkyozm_cafe=self.webpackChunkyozm_cafe||[]).push([[425],{"./src/components/Navbar.stories.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Default:()=>Default,__namedExportsOrder:()=>__namedExportsOrder,default:()=>Navbar_stories});var useQuery=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/useQuery.mjs"),index_esm=__webpack_require__("./node_modules/react-icons/pi/index.esm.js"),dist=__webpack_require__("./node_modules/react-router/dist/index.js"),react_router_dom_dist=__webpack_require__("./node_modules/react-router-dom/dist/index.js"),styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js");class ClientNetworkError extends Error{constructor(){super("인터넷에 연결할 수 없습니다")}}const src_client=new class Client{accessToken=null;async fetch(input,init){try{const response=await fetch(input,{...init,headers:{...init?.headers,...this.accessToken?{Authorization:`Bearer ${this.accessToken}`}:{}}});if(!response.ok)throw response;return response.json()}catch(error){throw new ClientNetworkError}}setAccessToken(accessToken){this.accessToken=accessToken}getCafes(page=1){return this.fetch(`/cafes?page=${page}`)}addFavoriteCafe(cafeId){return this.fetch(`/cafes/${cafeId}/likes`,{method:"POST"})}removeFavoriteCafe(cafeId){return this.fetch(`/cafes/${cafeId}/likes`,{method:"DELETE"})}requestAccessToken(provider,code){return this.fetch(`/auth/${provider}?code=${code}`,{method:"POST"})}};var jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const Navbar=()=>{const{pathname}=(0,dist.TH)(),{data:isLoggedIn}=(0,useQuery.a)(["isLoggedIn"],{enabled:!1,initialData:null!==src_client.accessToken});return(0,jsx_runtime.jsxs)(Container,{children:[(0,jsx_runtime.jsxs)(IconContainer,{to:"/",$isActive:"/"===pathname,children:[(0,jsx_runtime.jsx)(Icon,{as:index_esm.u6N}),(0,jsx_runtime.jsx)(IconName,{children:"홈"})]}),isLoggedIn?(0,jsx_runtime.jsxs)(IconContainer,{to:"/my-profile",$isActive:"/my-profile"===pathname,children:[(0,jsx_runtime.jsx)(ProfileImage,{src:"/images/profile-example.png",alt:"Profile"}),(0,jsx_runtime.jsx)(IconName,{children:"프로필"})]}):(0,jsx_runtime.jsxs)(IconContainer,{to:"/login",$isActive:"/login"===pathname,children:[(0,jsx_runtime.jsx)(Icon,{as:index_esm.VDT}),(0,jsx_runtime.jsx)(IconName,{children:"로그인"})]})]})};Navbar.displayName="Navbar";const components_Navbar=Navbar,IconName=styled_components_browser_esm.zo.span`
  font-size: small;
`,IconContainer=(0,styled_components_browser_esm.zo)(react_router_dom_dist.rU)`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;

  color: ${({$isActive,theme})=>$isActive?theme.color.primary:theme.color.gray};
  text-decoration: none;
`,Container=styled_components_browser_esm.zo.nav`
  display: flex;
  justify-content: space-evenly;

  width: 100%;
  height: 66px;

  border-top: 1px solid ${({theme})=>theme.color.line.secondary};
`,Icon=styled_components_browser_esm.zo.div`
  font-size: ${({theme})=>theme.fontSize["4xl"]};
`,ProfileImage=styled_components_browser_esm.zo.img`
  width: 36px;
  height: 36px;
  border-radius: 50%;
`,Navbar_stories={title:"Navbar",component:components_Navbar},Default={args:{}};Default.parameters={...Default.parameters,docs:{...Default.parameters?.docs,source:{originalSource:"{\n  args: {}\n}",...Default.parameters?.docs?.source}}};const __namedExportsOrder=["Default"]}}]);
//# sourceMappingURL=components-Navbar-stories.6e48fe3e.iframe.bundle.js.map