import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { ValidationPipe } from '@nestjs/common';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  await app.listen(process.env.PORT ?? 3000);
  app.useGlobalPipes(new ValidationPipe());
}
bootstrap().catch((err) => {
  // 如果 bootstrap 过程中出现任何错误，这里会捕获到
  console.error('Application failed to start', err);
});
