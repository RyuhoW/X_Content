INSERT INTO templates (userId, name, category, content, isDefault, createdAt, updatedAt)
VALUES
    (1, 'お知らせテンプレート', 'お知らせ', '【お知らせ】\n本日より新商品「XXX」の販売を開始いたしました。\n詳細は公式サイトをご確認ください。', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 'キャンペーンテンプレート', 'プロモーション', '【期間限定キャンペーン】\n対象商品を20%OFFで販売中！\n期間：○月○日まで\n#セール #キャンペーン', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 'イベントテンプレート', 'イベント', '【イベント開催】\n日時：○月○日\n場所：○○○\n内容：○○○\nご参加お待ちしております！', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO contents (accountId, text, hashtags, mediaUrls, templateId, status, createdAt, updatedAt)
VALUES
    (1, '【新商品情報】\n待望の新商品が登場！\n是非お試しください。', '#新商品 #新発売', '', 1, 'draft', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, '【セール情報】\n週末限定セール開催中！\n人気商品が最大30%OFF', '#セール #週末限定', '', 2, 'scheduled', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, '【イベント告知】\n明日の15時より新商品お披露目会を開催いたします。', '#イベント #新商品', '', 3, 'posted', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
