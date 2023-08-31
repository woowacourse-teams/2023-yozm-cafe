import { load } from "https://deno.land/std@0.200.0/dotenv/mod.ts";
import { serve } from "https://deno.land/std@0.200.0/http/server.ts";

await load({ export: true });

const getEnv = (envName, defaultValue = undefined) => {
  const envValue = Deno.env.get(envName) ?? defaultValue;
  if (envValue === undefined) {
    throw new Error(`${envName} 값을 설정해야 합니다`);
  }
  return envValue;
};

const PORT = Number(getEnv("PORT", 80));
const SLACK_WEBHOOK_URL = getEnv("SLACK_WEBHOOK_URL");
const SLACK_CHANNEL = getEnv("SLACK_CHANNEL");
const SLACK_USERNAME = getEnv("SLACK_USERNAME", "Notification");
const SLACK_ICON_EMOJI = getEnv("SLACK_ICON_EMOJI", ":ghost:");

const handler = async (request) => {
  /** @type {import('./types').WebhookMessage} */
  const body = await request.json();

  if (!("eventType" in body)) {
    return new Response(
      JSON.stringify({ message: "Not a valid webhook message format" }),
      { status: 400 }
    );
  }
  console.log(`Webhook 이벤트 발생: ${body.eventType}`);
  console.log(JSON.stringify(body));

  if (body.eventType === "BUILD_FINISHED") {
    const message = {
      channel: SLACK_CHANNEL,
      username: SLACK_USERNAME,
      icon_emoji: SLACK_ICON_EMOJI,
      blocks: [
        {
          type: "header",
          text: {
            type: "plain_text",
            text: `${
              body.payload.status === "SUCCESS" ? "✅빌드 성공" : "❌빌드 실패"
            }`,
            emoji: true,
          },
        },
        {
          type: "section",
          text: {
            type: "mrkdwn",
            text: `<${body.payload.webUrl}|🔗웹에서 보기>`,
          },
        },
        {
          type: "context",
          elements: [
            {
              type: "mrkdwn",
              text: `
                \`\`\`
                Build Name: ${body.payload.buildType.name}
                No: ${body.payload.number}
                Status: ${body.payload.status} (${body.payload.statusText})
                Branch Name: ${body.payload.branchName}
                Date: ${body.payload.finishDate.replace(
                  /(\d{4})(\d{2})(\d{2})T(\d{2})(\d{2})(\d{2})\+0000/,
                  "$1-$2-$3T$4:$5:$6.000Z"
                )}
                \`\`\`
              `
                .trim()
                .split("\n")
                .map((line) => line.trim())
                .join("\n"),
            },
          ],
        },
      ],
    };
    await fetch(SLACK_WEBHOOK_URL, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(message),
    });
  }

  return new Response(JSON.stringify({ message: "success" }), { status: 200 });
};

console.log(`HTTP webserver running on port ${PORT}`);
await serve(handler, { port: PORT });
