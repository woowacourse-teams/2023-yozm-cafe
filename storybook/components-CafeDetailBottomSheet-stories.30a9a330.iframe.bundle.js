"use strict";(self.webpackChunkyozm_cafe=self.webpackChunkyozm_cafe||[]).push([[699],{"./src/components/CafeDetailBottomSheet.stories.tsx":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Default:function(){return Default},__namedExportsOrder:function(){return __namedExportsOrder},default:function(){return CafeDetailBottomSheet_stories}});var mockData=__webpack_require__("./src/data/mockData.ts"),react=__webpack_require__("./node_modules/react/index.js"),index_esm=__webpack_require__("./node_modules/react-icons/bs/index.esm.js"),styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),client=__webpack_require__("./src/client.ts"),useSuspenseQuery=__webpack_require__("./src/hooks/useSuspenseQuery.ts");var hooks_useCafeMenus=cafeId=>(0,useSuspenseQuery.Z)({queryKey:["cafes",cafeId,"menus"],queryFn:()=>client.Z.getCafeMenu(cafeId)}),useScrollSnapGuard=__webpack_require__("./src/hooks/useScrollSnapGuard.ts"),CafeMenuMiniList=__webpack_require__("./src/components/CafeMenuMiniList.tsx"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const DAY_MAPPER={MONDAY:"월",TUESDAY:"화",WEDNESDAY:"수",THURSDAY:"목",FRIDAY:"금",SATURDAY:"토",SUNDAY:"일"},OpeningHoursDetail=props=>{const{openingHours:openingHours}=props,today=(new Date).toLocaleDateString("en-US",{weekday:"long"}).toUpperCase(),todayOpeningHour=openingHours.find((openingHour=>openingHour.day===today))??null;return(0,jsx_runtime.jsxs)(Container,{"aria-live":"polite",children:[(0,jsx_runtime.jsxs)(SummaryDetails,{children:[(0,jsx_runtime.jsx)(index_esm.ZEP,{}),(0,jsx_runtime.jsx)("h3",{children:(()=>{const currentTime=(()=>{const now=new Date;return`${now.getHours().toString().padStart(2,"0")}:${now.getMinutes().toString().padStart(2,"0")}`})();return!(!todayOpeningHour||!todayOpeningHour.opened)&&(today===todayOpeningHour.day&&todayOpeningHour.open<=currentTime&&currentTime<=todayOpeningHour.close)})()?"영업중":"영업 종료"}),(0,jsx_runtime.jsx)(Summary,{children:todayOpeningHour?.opened?`${DAY_MAPPER[todayOpeningHour.day]} ${todayOpeningHour.open} - ${todayOpeningHour.close}`:"휴무"})]}),(0,jsx_runtime.jsx)(Details,{children:openingHours.map((openingHour=>(0,jsx_runtime.jsxs)("li",{children:[DAY_MAPPER[openingHour.day]," ",openingHour.opened?`${openingHour.open} - ${openingHour.close}`:"휴무"]},openingHour.day)))})]})};OpeningHoursDetail.displayName="OpeningHoursDetail";var components_OpeningHoursDetail=OpeningHoursDetail;const Container=styled_components_browser_esm.zo.div``,Summary=styled_components_browser_esm.zo.h3``,Details=styled_components_browser_esm.zo.ul`
  display: flex;
  flex-direction: column;
  gap: ${({theme:theme})=>theme.space[1]};
  align-items: flex-end;

  color: ${({theme:theme})=>theme.color.gray};
`,SummaryDetails=styled_components_browser_esm.zo.div`
  display: flex;
  gap: ${({theme:theme})=>theme.space[2]};
  align-items: center;
  margin-bottom: ${({theme:theme})=>theme.space[1]};
`;try{OpeningHoursDetail.displayName="OpeningHoursDetail",OpeningHoursDetail.__docgenInfo={description:"",displayName:"OpeningHoursDetail",props:{openingHours:{defaultValue:null,description:"",name:"openingHours",required:!0,type:{name:"OpeningHour[]"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/OpeningHoursDetail.tsx#OpeningHoursDetail"]={docgenInfo:OpeningHoursDetail.__docgenInfo,name:"OpeningHoursDetail",path:"src/components/OpeningHoursDetail.tsx#OpeningHoursDetail"})}catch(__react_docgen_typescript_loader_error){}var onlineManager=__webpack_require__("./node_modules/@tanstack/query-core/build/lib/onlineManager.mjs"),QueryErrorResetBoundary=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/QueryErrorResetBoundary.mjs");class ErrorBoundary extends react.Component{constructor(props){super(props),this.state={error:null}}static getDerivedStateFromError(error){return{error:error}}componentDidCatch(error,errorInfo){const defaultCaught=error=>error instanceof Error,{caught:caught=defaultCaught}=this.props;if(!caught(error))throw error}render(){const{error:error}=this.state;if(error){if("fallbackRender"in this.props&&this.props.fallbackRender){const{fallbackRender:fallbackRender}=this.props;return fallbackRender({error:error,resetErrorBoundary:()=>this.resetErrorBoundary()})}if("FallbackComponent"in this.props&&this.props.FallbackComponent){const{FallbackComponent:FallbackComponent}=this.props;return(0,jsx_runtime.jsx)(FallbackComponent,{error:error,resetErrorBoundary:()=>this.resetErrorBoundary()})}return null}return this.props.children}resetErrorBoundary(){this.setState({error:null});const{onReset:onReset}=this.props;onReset?.()}}ErrorBoundary.displayName="ErrorBoundary";var components_ErrorBoundary=ErrorBoundary;try{ErrorBoundary.displayName="ErrorBoundary",ErrorBoundary.__docgenInfo={description:"자식 컴포넌트를 렌더링하는 중 에러가 발생하였을 때(throw error)\n대체 컴포넌트(fallback)을 렌더합니다.",displayName:"ErrorBoundary",props:{onReset:{defaultValue:null,description:"",name:"onReset",required:!1,type:{name:"(() => void)"}},caught:{defaultValue:null,description:"",name:"caught",required:!1,type:{name:"((error: unknown) => error is TError)"}},fallbackRender:{defaultValue:null,description:"",name:"fallbackRender",required:!1,type:{name:"(({ error, resetErrorBoundary }: FallbackProps<TError>) => ReactNode)"}},FallbackComponent:{defaultValue:null,description:"",name:"FallbackComponent",required:!1,type:{name:"ComponentType<FallbackProps<TError>>"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/ErrorBoundary.tsx#ErrorBoundary"]={docgenInfo:ErrorBoundary.__docgenInfo,name:"ErrorBoundary",path:"src/components/ErrorBoundary.tsx#ErrorBoundary"})}catch(__react_docgen_typescript_loader_error){}var Button=__webpack_require__("./src/components/Button.tsx");const ErrorRetryPrompt=Props=>{const{resetErrorBoundary:resetErrorBoundary}=Props;return(0,jsx_runtime.jsx)(ErrorRetryPrompt_Container,{children:(0,jsx_runtime.jsxs)(SentenceContainer,{children:[(0,jsx_runtime.jsx)(MainSentence,{children:"일시적인 장애가 발생했습니다"}),(0,jsx_runtime.jsx)(Sentence,{children:"요청사항을 처리하는데 실패했습니다."}),(0,jsx_runtime.jsx)(ButtonContainer,{children:(0,jsx_runtime.jsx)(Button.Z,{$fullWidth:!0,onClick:resetErrorBoundary,children:"다시 시도"})})]})})};ErrorRetryPrompt.displayName="ErrorRetryPrompt";var components_ErrorRetryPrompt=ErrorRetryPrompt;const ErrorRetryPrompt_Container=styled_components_browser_esm.ZP.section`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 100%;
  padding: ${({theme:theme})=>theme.space[5]};
`,SentenceContainer=styled_components_browser_esm.ZP.article`
  display: flex;
  flex-direction: column;
  gap: ${({theme:theme})=>theme.space[5]};
  align-items: center;
`,ButtonContainer=styled_components_browser_esm.ZP.div`
  width: 133px;
`,MainSentence=styled_components_browser_esm.ZP.h1`
  font-size: ${({theme:theme})=>theme.fontSize["2xl"]};
  font-weight: bold;
  color: ${({theme:theme})=>theme.color.text.secondary};
`,Sentence=styled_components_browser_esm.ZP.span`
  font-size: ${({theme:theme})=>theme.fontSize.sm};
  color: ${({theme:theme})=>theme.color.text.secondary};
  text-align: center;
`;try{ErrorRetryPrompt.displayName="ErrorRetryPrompt",ErrorRetryPrompt.__docgenInfo={description:"",displayName:"ErrorRetryPrompt",props:{resetErrorBoundary:{defaultValue:null,description:"",name:"resetErrorBoundary",required:!0,type:{name:"() => void"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/ErrorRetryPrompt.tsx#ErrorRetryPrompt"]={docgenInfo:ErrorRetryPrompt.__docgenInfo,name:"ErrorRetryPrompt",path:"src/components/ErrorRetryPrompt.tsx#ErrorRetryPrompt"})}catch(__react_docgen_typescript_loader_error){}const QueryErrorBoundary=props=>{const{children:children,onReset:onReset,...restProps}=props,[id,setId]=(0,react.useState)(0);return(0,react.useEffect)((()=>onlineManager.N.subscribe((()=>{onlineManager.N.isOnline()&&setId((id=>id+1))}))),[]),(0,jsx_runtime.jsx)(QueryErrorResetBoundary.k,{children:({reset:reset})=>(0,jsx_runtime.jsx)(components_ErrorBoundary,{onReset:()=>{reset(),onReset?.()},..."FallbackComponent"in restProps||"fallbackRender"in restProps?restProps:{FallbackComponent:components_ErrorRetryPrompt,...restProps},children:children},id)})};QueryErrorBoundary.displayName="QueryErrorBoundary";var components_QueryErrorBoundary=QueryErrorBoundary;try{QueryErrorBoundary.displayName="QueryErrorBoundary",QueryErrorBoundary.__docgenInfo={description:"react-query에서 `useQuery` 혹은 `useMutation` 등의 쿼리 함수를\n`suspense: true`로 사용 중 발생하는 에러를 위한\nErrorBoundary 컴포넌트입니다.\n\nErrorBoundary에서 다음 부가 기능이 추가되어있습니다\n* network 상태가 온라인으로 전환되었을 때 자동으로 재시도(error reset)\n* ErrorBoundary reset 시, query cache도 reset\n* 아무런 fallback이 주어지지 않았을 시 {@link ErrorRetryPrompt} 를 기본으로 사용",displayName:"QueryErrorBoundary",props:{onReset:{defaultValue:null,description:"",name:"onReset",required:!1,type:{name:"(() => void)"}},caught:{defaultValue:null,description:"",name:"caught",required:!1,type:{name:"((error: unknown) => error is TError)"}},fallbackRender:{defaultValue:null,description:"",name:"fallbackRender",required:!1,type:{name:"(({ error, resetErrorBoundary }: FallbackProps<TError>) => ReactNode)"}},FallbackComponent:{defaultValue:null,description:"",name:"FallbackComponent",required:!1,type:{name:"ComponentType<FallbackProps<TError>>"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/QueryErrorBoundary.tsx#QueryErrorBoundary"]={docgenInfo:QueryErrorBoundary.__docgenInfo,name:"QueryErrorBoundary",path:"src/components/QueryErrorBoundary.tsx#QueryErrorBoundary"})}catch(__react_docgen_typescript_loader_error){}const CafeDetailBottomSheet=props=>{const{cafe:cafe,onClose:onClose}=props,scrollSnapGuardHandlers=(0,useScrollSnapGuard.Z)();(0,react.useEffect)((()=>(document.addEventListener("click",onClose),()=>document.removeEventListener("click",onClose))),[onClose]);return(0,jsx_runtime.jsxs)(CafeDetailBottomSheet_Container,{onClick:event=>{event.stopPropagation()},role:"dialog","aria-modal":"true",...scrollSnapGuardHandlers,children:[(0,jsx_runtime.jsx)(CloseButton,{children:(0,jsx_runtime.jsx)(CloseIcon,{onClick:onClose})}),(0,jsx_runtime.jsx)(Title,{children:cafe.name}),(0,jsx_runtime.jsx)(Spacer,{$size:"4"}),(0,jsx_runtime.jsx)(components_QueryErrorBoundary,{children:(0,jsx_runtime.jsx)(react.Suspense,{children:(0,jsx_runtime.jsx)(CafeMenu,{cafeId:cafe.id})})}),(0,jsx_runtime.jsxs)(InfoContainer,{children:[(0,jsx_runtime.jsxs)(LocationDetail,{children:[(0,jsx_runtime.jsx)(NaverMapIcon,{}),(0,jsx_runtime.jsx)("a",{href:cafe.detail.mapUrl,target:"_blank",rel:"noopener noreferrer",children:(0,jsx_runtime.jsx)(LocationName,{children:cafe.address})})]}),(0,jsx_runtime.jsx)(OpeningHoursDetails,{children:(0,jsx_runtime.jsx)(components_OpeningHoursDetail,{openingHours:cafe.detail.openingHours})})]}),(0,jsx_runtime.jsx)(Spacer,{$size:"6"}),(0,jsx_runtime.jsx)(Content,{children:cafe.detail.description.split("\n").map(((paragraph,index)=>(0,jsx_runtime.jsx)("p",{children:paragraph},index)))}),(0,jsx_runtime.jsx)(MoreContentHintGradient,{})]})};CafeDetailBottomSheet.displayName="CafeDetailBottomSheet";var components_CafeDetailBottomSheet=CafeDetailBottomSheet;const CafeDetailBottomSheet_Container=styled_components_browser_esm.zo.div`
  position: absolute;
  bottom: 0;

  overflow-y: auto;
  display: flex;
  flex-direction: column;

  width: 100%;
  height: 450px;
  padding: ${({theme:theme})=>theme.space[4]};
  padding-bottom: 0;

  color: ${({theme:theme})=>theme.color.text.primary};
  text-shadow: none;

  background: ${({theme:theme})=>theme.color.white};

  & svg {
    filter: none !important;
  }
`,Spacer=styled_components_browser_esm.zo.div`
  min-height: ${({theme:theme,$size:$size})=>theme.space[$size]};
`,Title=styled_components_browser_esm.zo.h1`
  font-size: ${({theme:theme})=>theme.fontSize["3xl"]};
  font-weight: bolder;
`,InfoContainer=styled_components_browser_esm.zo.div`
  display: flex;
  flex-direction: column;
  gap: ${({theme:theme})=>theme.space[2]};
`,OpeningHoursDetails=styled_components_browser_esm.zo.div`
  display: flex;
  gap: ${({theme:theme})=>theme.space[2]};
  align-items: flex-start;
`,LocationDetail=styled_components_browser_esm.zo.div`
  display: flex;
  gap: ${({theme:theme})=>theme.space[2]};
  align-items: center;
  color: ${({theme:theme})=>theme.color.secondary};
`,NaverMapIcon=styled_components_browser_esm.zo.img.attrs({src:"/assets/naver-map-icon.png",alt:"네이버 지도 아이콘"})`
  width: 16px;
  height: 16px;
`,LocationName=styled_components_browser_esm.zo.h3`
  display: flex;
  gap: ${({theme:theme})=>theme.space[1]};
  align-items: center;
`,Content=styled_components_browser_esm.zo.div`
  display: flex;
  flex-direction: column;
  gap: ${({theme:theme})=>theme.space[2]};
`,MoreContentHintGradient=styled_components_browser_esm.zo.div`
  position: sticky;
  bottom: 0;

  width: 100%;
  min-height: ${({theme:theme})=>theme.space[16]};

  background: linear-gradient(transparent, white);
`,CloseButton=styled_components_browser_esm.zo.button`
  display: flex;
  justify-content: flex-end;
  width: 100%;
`,CloseIcon=(0,styled_components_browser_esm.zo)(index_esm.z3f)`
  font-size: ${({theme:theme})=>theme.fontSize["2xl"]};
`,CafeMenu=props=>{const{cafeId:cafeId}=props,{data:{menus:menus}}=hooks_useCafeMenus(cafeId),recommendedMenus=menus.filter((menuItem=>menuItem.isRecommended));return recommendedMenus.length>0&&(0,jsx_runtime.jsxs)(jsx_runtime.Fragment,{children:[(0,jsx_runtime.jsx)(CafeMenuMiniList.Z,{menus:recommendedMenus}),(0,jsx_runtime.jsx)(Spacer,{$size:"8"})]})};try{CafeDetailBottomSheet.displayName="CafeDetailBottomSheet",CafeDetailBottomSheet.__docgenInfo={description:"",displayName:"CafeDetailBottomSheet",props:{cafe:{defaultValue:null,description:"",name:"cafe",required:!0,type:{name:"Cafe"}},onClose:{defaultValue:null,description:"",name:"onClose",required:!0,type:{name:"() => void"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/CafeDetailBottomSheet.tsx#CafeDetailBottomSheet"]={docgenInfo:CafeDetailBottomSheet.__docgenInfo,name:"CafeDetailBottomSheet",path:"src/components/CafeDetailBottomSheet.tsx#CafeDetailBottomSheet"})}catch(__react_docgen_typescript_loader_error){}var CafeDetailBottomSheet_stories={title:"CafeDetailBottomSheet",component:components_CafeDetailBottomSheet};const Default={args:{cafe:mockData.on[0]}};Default.parameters={...Default.parameters,docs:{...Default.parameters?.docs,source:{originalSource:"{\n  args: {\n    cafe: cafes[0]\n  }\n}",...Default.parameters?.docs?.source}}};const __namedExportsOrder=["Default"]},"./src/components/Button.tsx":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){var styled_components__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/react/jsx-runtime.js");const Button=props=>{const{children:children,$variant:$variant="default",$fullWidth:$fullWidth=!1,$fullHeight:$fullHeight=!1,...rest}=props;return(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(Container,{$variant:$variant,$fullWidth:$fullWidth,$fullHeight:$fullHeight,...rest,children:children})};Button.displayName="Button",__webpack_exports__.Z=Button;const ButtonVariants={secondary:styled_components__WEBPACK_IMPORTED_MODULE_1__.iv`
    color: ${props=>props.theme.color.white};
    background-color: ${props=>props.theme.color.secondary};
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
  padding: ${({theme:theme})=>theme.space[1.5]} ${({theme:theme})=>theme.space[2]};
  font-size: 16px;
  font-weight: 500;
  border-radius: 40px;
  ${props=>ButtonVariants[props.$variant||"default"]}
  ${props=>props.$fullWidth&&"width: 100%;"}
  ${props=>props.$fullHeight&&"height: 100%;"}
`;try{Button.displayName="Button",Button.__docgenInfo={description:"",displayName:"Button",props:{$variant:{defaultValue:null,description:"",name:"$variant",required:!1,type:{name:"enum",value:[{value:'"secondary"'},{value:'"default"'},{value:'"outlined"'}]}},$fullWidth:{defaultValue:null,description:"",name:"$fullWidth",required:!1,type:{name:"boolean"}},$fullHeight:{defaultValue:null,description:"",name:"$fullHeight",required:!1,type:{name:"boolean"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/Button.tsx#Button"]={docgenInfo:Button.__docgenInfo,name:"Button",path:"src/components/Button.tsx#Button"})}catch(__react_docgen_typescript_loader_error){}},"./src/components/CafeMenuMiniList.tsx":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){var react_icons_pi__WEBPACK_IMPORTED_MODULE_3__=__webpack_require__("./node_modules/react-icons/pi/index.esm.js"),styled_components__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),_utils_Resource__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./src/utils/Resource.ts"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/react/jsx-runtime.js");const CafeMenuMiniList=props=>{const{menus:menus}=props;return(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(MenuList,{children:menus.map((menuItem=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(MenuListItem,{menuItem:menuItem},menuItem.id)))})};CafeMenuMiniList.displayName="CafeMenuMiniList",__webpack_exports__.Z=CafeMenuMiniList;const MenuList=styled_components__WEBPACK_IMPORTED_MODULE_1__.zo.ul`
  overflow-y: auto;
  display: flex;
  flex-shrink: 0;
  gap: ${({theme:theme})=>theme.space[4]};
`,MenuListItem=props=>{const{menuItem:menuItem}=props;return(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxs)(MenuListItemContainer,{children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(MenuItemImageContainer,{children:menuItem.imageUrl?(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(MenuItemImage,{src:_utils_Resource__WEBPACK_IMPORTED_MODULE_2__.Z.getImageUrl({size:"100",filename:menuItem.imageUrl})}):(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(MenuItemImageAlt,{children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(MenuItemImageAltIcon,{})})}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(MenuItemName,{children:menuItem.name})]})};MenuListItem.displayName="MenuListItem";const MenuListItemContainer=styled_components__WEBPACK_IMPORTED_MODULE_1__.zo.li`
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
  align-items: center;

  width: 70px;
  height: 100%;
`,MenuItemImageContainer=styled_components__WEBPACK_IMPORTED_MODULE_1__.zo.div`
  width: 100%;
  height: 100%;
  padding: ${({theme:theme})=>theme.space[2]};

  & > * {
    aspect-ratio: 1 / 1;
    width: 100%;
  }
`,MenuItemImage=styled_components__WEBPACK_IMPORTED_MODULE_1__.zo.img`
  display: block;
  object-fit: cover;
  border-radius: 50%;
`,MenuItemImageAlt=styled_components__WEBPACK_IMPORTED_MODULE_1__.zo.div`
  display: flex;
  align-items: center;
  justify-content: center;

  background: #dedede;
  border-radius: 50%;
`,MenuItemImageAltIcon=(0,styled_components__WEBPACK_IMPORTED_MODULE_1__.zo)(react_icons_pi__WEBPACK_IMPORTED_MODULE_3__.yAJ)`
  width: 50%;
  height: 50%;
`,MenuItemName=styled_components__WEBPACK_IMPORTED_MODULE_1__.zo.div`
  font-size: ${({theme:theme})=>theme.fontSize.xs};
`;styled_components__WEBPACK_IMPORTED_MODULE_1__.zo.hr`
  margin: ${({theme:theme})=>theme.space[2]} ${({theme:theme})=>theme.space[1]};
  border: none;
  border-left: 1px solid ${({theme:theme})=>theme.color.gray};
`;try{CafeMenuMiniList.displayName="CafeMenuMiniList",CafeMenuMiniList.__docgenInfo={description:"",displayName:"CafeMenuMiniList",props:{menus:{defaultValue:null,description:"",name:"menus",required:!0,type:{name:"CafeMenuItem[]"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/CafeMenuMiniList.tsx#CafeMenuMiniList"]={docgenInfo:CafeMenuMiniList.__docgenInfo,name:"CafeMenuMiniList",path:"src/components/CafeMenuMiniList.tsx#CafeMenuMiniList"})}catch(__react_docgen_typescript_loader_error){}},"./src/hooks/useScrollSnapGuard.ts":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){var react__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/react/index.js");__webpack_exports__.Z=()=>{const preventPropagation=(0,react__WEBPACK_IMPORTED_MODULE_0__.useCallback)((event=>{event.stopPropagation()}),[]);return(0,react__WEBPACK_IMPORTED_MODULE_0__.useMemo)((()=>({onTouchStart:preventPropagation,onTouchMove:preventPropagation,onTouchEnd:preventPropagation,onMouseDown:preventPropagation,onMouseMove:preventPropagation,onMouseUp:preventPropagation,onMouseLeave:preventPropagation,onWheel:preventPropagation})),[preventPropagation])}},"./src/hooks/useSuspenseQuery.ts":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){var _tanstack_react_query__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/useQuery.mjs");__webpack_exports__.Z=function useSuspenseQuery(options){const queryResult=(0,_tanstack_react_query__WEBPACK_IMPORTED_MODULE_0__.a)({...options,suspense:!0});return{...queryResult,isIdle:"isIdle"in queryResult&&queryResult.isIdle}}},"./src/utils/Resource.ts":function(__unused_webpack_module,__webpack_exports__){class Resource{constructor(){}static getImageUrl(options){const{filename:filename,size:size}=options;return Resource.joinPath("/images",size,filename)}static getAssetUrl(options){const{filename:filename}=options;return Resource.joinPath("/assets",filename)}static joinPath(...paths){return paths.map(((part,index)=>(index>0&&(part=part.replace(/^\/+/,"")),index!==paths.length-1&&(part=part.replace(/\/+$/,"")),part))).join("/")}}__webpack_exports__.Z=Resource}}]);
//# sourceMappingURL=components-CafeDetailBottomSheet-stories.30a9a330.iframe.bundle.js.map