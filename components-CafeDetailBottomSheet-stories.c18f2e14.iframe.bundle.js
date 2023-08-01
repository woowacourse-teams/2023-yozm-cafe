"use strict";(self.webpackChunkyozm_cafe=self.webpackChunkyozm_cafe||[]).push([[699],{"./src/components/CafeDetailBottomSheet.stories.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Default:()=>Default,__namedExportsOrder:()=>__namedExportsOrder,default:()=>CafeDetailBottomSheet_stories});var react=__webpack_require__("./node_modules/react/index.js"),index_esm=__webpack_require__("./node_modules/react-icons/bs/index.esm.js"),styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const DAY_MAPPER={MONDAY:"월",TUESDAY:"화",WEDNESDAY:"수",THURSDAY:"목",FRIDAY:"금",SATURDAY:"토",SUNDAY:"일"},OpeningHoursDetail=({openingHours})=>{const today=(new Date).toLocaleDateString("en-US",{weekday:"long"}).toUpperCase(),todayOpeningHour=openingHours.find((openingHour=>openingHour.day===today))??null;return(0,jsx_runtime.jsxs)(Container,{children:[(0,jsx_runtime.jsxs)(SummaryDetails,{children:[(0,jsx_runtime.jsx)(index_esm.ZEP,{}),(0,jsx_runtime.jsx)("h3",{children:(()=>{const currentTime=(()=>{const now=new Date;return`${now.getHours().toString().padStart(2,"0")}:${now.getMinutes().toString().padStart(2,"0")}`})();return!(!todayOpeningHour||!todayOpeningHour.opened)&&(today===todayOpeningHour.day&&todayOpeningHour.open<=currentTime&&currentTime<=todayOpeningHour.close)})()?"영업중":"영업 종료"}),(0,jsx_runtime.jsx)(Summary,{children:todayOpeningHour?.opened?`${DAY_MAPPER[todayOpeningHour.day]} ${todayOpeningHour.open} - ${todayOpeningHour.close}`:"휴무"})]}),(0,jsx_runtime.jsx)(Details,{children:openingHours.map((openingHour=>(0,jsx_runtime.jsxs)("li",{children:[DAY_MAPPER[openingHour.day]," ",openingHour.opened?`${openingHour.open} - ${openingHour.close}`:"휴무"]},openingHour.day)))})]})};OpeningHoursDetail.displayName="OpeningHoursDetail";const components_OpeningHoursDetail=OpeningHoursDetail,Container=styled_components_browser_esm.zo.div``,Summary=styled_components_browser_esm.zo.h3``,Details=styled_components_browser_esm.zo.ul`
  display: flex;
  flex-direction: column;
  gap: ${({theme})=>theme.space[1]};
  align-items: flex-end;

  color: ${({theme})=>theme.color.gray};
`,SummaryDetails=styled_components_browser_esm.zo.div`
  display: flex;
  gap: ${({theme})=>theme.space[2]};
  align-items: center;
  margin-bottom: ${({theme})=>theme.space[1]};
`;try{OpeningHoursDetail.displayName="OpeningHoursDetail",OpeningHoursDetail.__docgenInfo={description:"",displayName:"OpeningHoursDetail",props:{openingHours:{defaultValue:null,description:"",name:"openingHours",required:!0,type:{name:"OpeningHour[]"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/OpeningHoursDetail.tsx#OpeningHoursDetail"]={docgenInfo:OpeningHoursDetail.__docgenInfo,name:"OpeningHoursDetail",path:"src/components/OpeningHoursDetail.tsx#OpeningHoursDetail"})}catch(__react_docgen_typescript_loader_error){}const CafeDetailBottomSheet=({show,cafe,onClose})=>{(0,react.useEffect)((()=>(document.addEventListener("click",onClose),()=>document.removeEventListener("click",onClose))),[onClose]);return(0,jsx_runtime.jsxs)(CafeDetailBottomSheet_Container,{$show:show??!1,onClick:event=>{event.stopPropagation()},children:[(0,jsx_runtime.jsx)(CloseButton,{children:(0,jsx_runtime.jsx)(CloseIcon,{onClick:onClose})}),(0,jsx_runtime.jsx)(Title,{children:cafe.name}),(0,jsx_runtime.jsxs)(InfoContainer,{children:[(0,jsx_runtime.jsxs)(LocationDetail,{children:[(0,jsx_runtime.jsx)(index_esm.Pat,{}),(0,jsx_runtime.jsx)("a",{href:cafe.detail.mapUrl,target:"_blank",children:(0,jsx_runtime.jsxs)(LocationName,{children:[cafe.address," ",(0,jsx_runtime.jsx)(index_esm.y_S,{})]})})]}),(0,jsx_runtime.jsx)(OpeningHoursDetails,{children:(0,jsx_runtime.jsx)(components_OpeningHoursDetail,{openingHours:cafe.detail.openingHours})})]}),(0,jsx_runtime.jsx)(Content,{children:cafe.detail.description.split("\n").map((paragraph=>(0,jsx_runtime.jsx)("p",{children:paragraph})))}),(0,jsx_runtime.jsx)(MoreContentHintGradient,{})]})};CafeDetailBottomSheet.displayName="CafeDetailBottomSheet";const components_CafeDetailBottomSheet=CafeDetailBottomSheet,CafeDetailBottomSheet_Container=styled_components_browser_esm.zo.div`
  position: absolute;
  bottom: 0;

  overflow-y: auto;
  display: ${props=>props.$show?"flex":"none"};
  flex-direction: column;

  width: 100%;
  height: 450px;
  padding: ${({theme})=>theme.space[4]};
  padding-bottom: ${({theme})=>theme.space[16]};

  color: ${({theme})=>theme.color.text.primary};
  text-shadow: none;

  background: ${({theme})=>theme.color.white};

  &::-webkit-scrollbar {
    display: none;
  }
  & svg {
    filter: none !important;
  }
`,Title=styled_components_browser_esm.zo.h1`
  margin-bottom: ${({theme})=>theme.space[4]};
  font-size: ${({theme})=>theme.fontSize["3xl"]};
  font-weight: bolder;
`,InfoContainer=styled_components_browser_esm.zo.div`
  display: flex;
  flex-direction: column;
  gap: ${({theme})=>theme.space[2]};
`,OpeningHoursDetails=styled_components_browser_esm.zo.div`
  display: flex;
  gap: ${({theme})=>theme.space[2]};
  align-items: flex-start;
`,LocationDetail=styled_components_browser_esm.zo.div`
  display: flex;
  gap: ${({theme})=>theme.space[2]};
  align-items: center;
`,LocationName=styled_components_browser_esm.zo.h3`
  display: flex;
  gap: ${({theme})=>theme.space[1]};
  align-items: center;
`,Content=styled_components_browser_esm.zo.div`
  display: flex;
  flex-direction: column;
  gap: ${({theme})=>theme.space[2]};
  margin-top: ${({theme})=>theme.space[5]};
`,MoreContentHintGradient=styled_components_browser_esm.zo.div`
  position: fixed;
  bottom: 0;

  width: 475px; /* FIXME: 하드코딩 대신 부모 크기 기반으로 설정하도록 변경하기 */
  height: ${({theme})=>theme.space[16]};

  background: linear-gradient(transparent, white);
`,CloseButton=styled_components_browser_esm.zo.button`
  display: flex;
  justify-content: flex-end;
  width: 100%;
`,CloseIcon=(0,styled_components_browser_esm.zo)(index_esm.z3f)`
  cursor: pointer;
  font-size: ${({theme})=>theme.fontSize["2xl"]};
`;try{CafeDetailBottomSheet.displayName="CafeDetailBottomSheet",CafeDetailBottomSheet.__docgenInfo={description:"",displayName:"CafeDetailBottomSheet",props:{show:{defaultValue:null,description:"",name:"show",required:!1,type:{name:"boolean"}},cafe:{defaultValue:null,description:"",name:"cafe",required:!0,type:{name:"Cafe"}},onClose:{defaultValue:null,description:"",name:"onClose",required:!0,type:{name:"() => void"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/CafeDetailBottomSheet.tsx#CafeDetailBottomSheet"]={docgenInfo:CafeDetailBottomSheet.__docgenInfo,name:"CafeDetailBottomSheet",path:"src/components/CafeDetailBottomSheet.tsx#CafeDetailBottomSheet"})}catch(__react_docgen_typescript_loader_error){}const CafeDetailBottomSheet_stories={title:"CafeDetailBottomSheet",component:components_CafeDetailBottomSheet},Default={args:{cafe:{id:1,name:"성수 카페",address:"성수로 1길",images:["/images/cafe-image-1.png","/images/cafe-image-2.png","/images/cafe-image-3.png","/images/cafe-image-4.png","/images/cafe-image-5.png"],isLiked:!1,likeCount:1,detail:{mapUrl:"https://map.kakao/~~",description:"우리 카페는 이뻐용",openingHours:[{day:"MONDAY",open:"2023-07-14T15:27:48.222433",close:"2023-07-14T15:27:48.222448",opened:!0},{day:"TUESDAY",open:"2023-07-14T15:27:48.222455",close:"2023-07-14T15:27:48.222457",opened:!0},{day:"WEDNESDAY",open:"2023-07-14T15:27:48.222459",close:"2023-07-14T15:27:48.222461",opened:!0},{day:"THURSDAY",open:"2023-07-14T15:27:48.222463",close:"2023-07-14T15:27:48.222465",opened:!0},{day:"FRIDAY",open:"2023-07-14T15:27:48.222468",close:"2023-07-14T15:27:48.22247",opened:!0},{day:"SATURDAY",open:"2023-07-14T15:27:48.222472",close:"2023-07-14T15:27:48.222475",opened:!0},{day:"SUNDAY",open:"2023-07-14T15:27:48.222477",close:"2023-07-14T15:27:48.222479",opened:!0}]}}}};Default.parameters={...Default.parameters,docs:{...Default.parameters?.docs,source:{originalSource:"{\n  args: {\n    cafe: cafes[0]\n  }\n}",...Default.parameters?.docs?.source}}};const __namedExportsOrder=["Default"]}}]);
//# sourceMappingURL=components-CafeDetailBottomSheet-stories.c18f2e14.iframe.bundle.js.map